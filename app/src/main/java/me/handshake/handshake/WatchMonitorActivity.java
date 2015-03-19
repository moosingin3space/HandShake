package me.handshake.handshake;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.ParseUser;


public class WatchMonitorActivity extends ActionBarActivity {

    private ListView theView;
    private ArrayAdapter<String> theAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_monitor);
        theView = (ListView) findViewById(R.id.listView);
        theAdapter = new ArrayAdapter<String>(this, R.layout.simple_text_view);
        theView.setAdapter(theAdapter);

        PebbleTools.registerHandshakeHandler(this, new FindHandshakeCallback() {
            @Override
            public void done(ParseUser user) {
                theAdapter.add("Email: " + user.getEmail() + "\nGithub: " + user.get("github") + "\nLinkedIn: " + user.get("linkedin"));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_watch_monitor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
