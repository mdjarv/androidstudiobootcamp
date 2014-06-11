package org.mdjarv.androidstudiobootcamp.androidstudiobootcamp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.mdjarv.androidstudiobootcamp.androidstudiobootcamp.data.Gist;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;


public class MainActivity extends Activity {
    private static final String GIST_URL = "https://api.github.com/gists";
    private static final String TAG = MainActivity.class.getSimpleName();

    private ArrayList<Gist> gistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gistArrayList = new ArrayList<Gist>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                populateGistArrayList();
                updateGistArrayView();
                return null;
            }
        }.execute();
    }

    private void updateGistArrayView() {
        if (gistArrayList == null || gistArrayList.isEmpty())
            return;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = getLayoutInflater();
                LinearLayout gistLinearLayout = (LinearLayout) findViewById(R.id.gistLinearLayout);
                gistLinearLayout.removeAllViews();

                for (Gist gist : gistArrayList) {
                    View gistView = inflater.inflate(R.layout.gist, null);

                    ((TextView) gistView.findViewById(R.id.gistIdTextView)).setText(gist.getId());
                    ((TextView) gistView.findViewById(R.id.ownerTextView)).setText(gist.getOwner());
                    ((TextView) gistView.findViewById(R.id.descriptionTextView)).setText(gist.getDescription());

                    gistLinearLayout.addView(gistView);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void populateGistArrayList() {
        try {
            gistArrayList.clear();

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(GIST_URL));
            HttpResponse response = httpClient.execute(request);
            ObjectMapper objectMapper = new ObjectMapper();

            ArrayList<LinkedHashMap> rootAsArray = objectMapper.readValue(response.getEntity().getContent(), ArrayList.class);

            for (LinkedHashMap gistMap : rootAsArray) {
                Gist gist = new Gist();

                try {
                    gist.setId((String) gistMap.get("id"));
                } catch (NullPointerException e) {
                    gist.setDescription("No id");
                }

                try {
                    LinkedHashMap owner = (LinkedHashMap) gistMap.get("owner");
                    gist.setOwner((String) owner.get("login"));
                } catch (NullPointerException e) {
                    gist.setOwner("Owner not found");
                }

                try {
                    gist.setDescription((String) gistMap.get("description"));
                } catch (NullPointerException e) {
                    gist.setDescription("No description");
                }
                gistArrayList.add(gist);
            }

            Log.i(TAG, "Fetched Gists");
        } catch (Exception e) {
            Log.e(TAG, "Error fetching Gists", e);
        }
    }
}
