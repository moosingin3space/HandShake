package me.handshake.handshake;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.SignUpCallback;


public class SignUpActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView signUpLink;
    private EditText emailField;
    private EditText passwordField;
    private EditText githubField;
    private EditText linkedinField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpLink = (TextView) findViewById(R.id.signUpLink);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        githubField = (EditText) findViewById(R.id.githubField);
        linkedinField = (EditText) findViewById(R.id.linkedinField);

        signUpLink.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_up, menu);
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
        // sign up
        ParseTools.newUser("", emailField.getText().toString(),
                passwordField.getText().toString(),
                githubField.getText().toString(),
                linkedinField.getText().toString(),
                new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            LogTools.debug("signup success");
                            Intent intent = new Intent(SignUpActivity.this, WatchMonitorActivity.class);
                            SignUpActivity.this.startActivity(intent);
                            SignUpActivity.this.finishActivity(0);
                        } else {
                            LogTools.error("signup failed " + e);
                        }
                    }
                });
    }
}
