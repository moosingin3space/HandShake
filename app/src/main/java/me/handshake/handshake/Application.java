package me.handshake.handshake;

import com.parse.Parse;
import com.parse.ParseInstallation;

/**
 * Created by nathan on 3/1/15.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "OUQCL4EOiQL7kdNjM0BVyrjRTkEqkAHYP7emrnCJ", "RaNDyjQBAM7Dd7kN4AJZHTVbyCKVoVMxmUJD10Cb");
        ParseInstallation.getCurrentInstallation().saveInBackground();
    }
}
