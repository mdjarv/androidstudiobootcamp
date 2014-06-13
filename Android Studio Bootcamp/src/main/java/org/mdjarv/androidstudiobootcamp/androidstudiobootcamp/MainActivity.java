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
    // TAG is set to the current class name for use when logging events
    private static final String TAG = MainActivity.class.getSimpleName();

    // Github Gist API
    private static final String GIST_URL = "https://api.github.com/gists";

    private ArrayList<Gist> gistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gistArrayList = new ArrayList<Gist>();
    }

    /**
     * onResume is executed every time the application gains focus.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Because network communication is forbidden in the UI thread we execute it in the
        // background via an AsyncTask, which provides a simple way to do background threads.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                populateGistArrayList();
                updateGistArrayView();
                return null;
            }
        }.execute();
    }

    /**
     * Update the LinearLayout view with the Gists that are in the gistArrayList.
     *
     * Because we are about to change UI elements we have to make sure our code is run on the
     * UI thread or we will get an exception. Using runOnUiThread(new Runnable() {...});
     * provides us with an easy to use method for this regardless of what thread calls this
     * method.
     */
    private void updateGistArrayView() {
        // If there are no Gist objects, do nothing
        if (gistArrayList == null || gistArrayList.isEmpty())
            return;

        // Make sure we are on the UI thread first
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // A LayoutInflater can inflate a layout xml into an instantiated layout object.
                LayoutInflater inflater = getLayoutInflater();

                /**
                 * findViewById(...) is context sensitive, in this case we search the current
                 * activitys main layout defined by the setContentView(...) in onCreate()
                 */
                LinearLayout gistLinearLayout = (LinearLayout) findViewById(R.id.gistLinearLayout);

                // Remove all children in the linear layout
                gistLinearLayout.removeAllViews();

                for (Gist gist : gistArrayList) {
                    // Inflate a new gist layout into a View object
                    View gistView = inflater.inflate(R.layout.gist, null);

                    /**
                     * Set our TextView fields. Here the findViewById is run on the newly inflated
                     * gistView object so it will only search the children of this object and not
                     * the main layout.
                     */
                    ((TextView) gistView.findViewById(R.id.gistIdTextView)).setText(gist.getId());
                    ((TextView) gistView.findViewById(R.id.ownerTextView)).setText(gist.getOwner());
                    ((TextView) gistView.findViewById(R.id.descriptionTextView)).setText(gist.getDescription());

                    gistLinearLayout.addView(gistView);
                }

            }
        });
    }

    /**
     * Fetch Gists from API and convert them to Gist objects. Make sure this is not executed in
     * the UI thread or you might get an exception.
     */
    private void populateGistArrayList() {
        try {
            Log.i(TAG, "Fetching Gists");
            gistArrayList.clear();

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(GIST_URL));
            HttpResponse response = httpClient.execute(request);

            // JSON data received, time to convert it into objects
            ObjectMapper objectMapper = new ObjectMapper();

            // Using the Jackson libraries it is possible to have the ObjectMapper automatically
            // serialize and deserialize into your custom classes if these are modelled after the
            // JSON response. Read more at https://github.com/FasterXML/jackson-databind

            // Here I have been lazy and just parse the JSON into an array of LinkedHashMaps.
            ArrayList<LinkedHashMap> rootAsArray = objectMapper.readValue(response.getEntity().getContent(), ArrayList.class);

            for (LinkedHashMap gistMap : rootAsArray) {
                Gist gist = new Gist();

                // Extract the values from the LinkedHashMap, not the prettiest code, but it works
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

                // Add the newly created Gist object to the gistArrayList
                gistArrayList.add(gist);
            }

            Log.i(TAG, "Fetched Gists");
        } catch (Exception e) {
            Log.e(TAG, "Error fetching Gists", e);
        }
    }
}
