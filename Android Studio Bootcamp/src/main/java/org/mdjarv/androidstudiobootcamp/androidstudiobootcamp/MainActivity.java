package org.mdjarv.androidstudiobootcamp.androidstudiobootcamp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.mdjarv.androidstudiobootcamp.androidstudiobootcamp.data.Gist;

import java.util.ArrayList;


public class MainActivity extends Activity {

    private ArrayList<Gist> gistArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gistArrayList = new ArrayList<Gist>();

        populateGistArrayList();
        updateGistArrayView();
    }

    private void updateGistArrayView() {
        if (gistArrayList == null)
            return;

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout gistLinearLayout = (LinearLayout) findViewById(R.id.gistLinearLayout);
        gistLinearLayout.removeAllViews();

        for (Gist gist : gistArrayList) {
            View gistView = inflater.inflate(R.layout.gist, null);

            ((TextView) gistView.findViewById(R.id.ownerTextView)).setText(gist.getOwner());
            ((TextView) gistView.findViewById(R.id.descriptionTextView)).setText(gist.getDescription());

            gistLinearLayout.addView(gistView);
        }
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
        gistArrayList.add(new Gist("mdjarv", "Build some code!"));
        gistArrayList.add(new Gist("mdjarv", "Build more code!"));
        gistArrayList.add(new Gist("mdjarv", "Build ALL the code!"));
    }
}
