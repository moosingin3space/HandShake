package me.handshake.handshake;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView signInButton;
    private TextView signUpButton;
    private EditText emailField;
    private EditText passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() != null) {
            Intent intent = new Intent(this, WatchMonitorActivity.class);
            startActivity(intent);
            finishActivity(0);
            return;
        }
        setContentView(R.layout.activity_main);
        signInButton = (TextView) findViewById(R.id.signInButton);
        signUpButton = (TextView) findViewById(R.id.signUpButton);
        emailField = (EditText) findViewById(R.id.emailText);
        passwordField = (EditText) findViewById(R.id.passwordText);
        signInButton.setOnClickListener(this);
        signUpButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        if (v == signInButton) {
            ParseUser.logInInBackground(emailField.getText().toString(), passwordField.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    if (parseUser != null) {
                        LogTools.debug("successful login");
                        Intent intent = new Intent(MainActivity.this, WatchMonitorActivity.class);
                        MainActivity.this.startActivity(intent);
                    } else {
                        LogTools.error("login failed " + e);
                    }
                }
            });
        } else {
            // launch sign-up
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        }
    }
}
