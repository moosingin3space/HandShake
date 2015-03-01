package me.handshake.handshake;

import android.util.Log;

/**
 * Created by nathan on 3/1/15.
 */
public final class LogTools {
    private static final String TAG = "HandShake";
    private LogTools() {}

    public static void debug(String st) {
        Log.d(TAG, st);
    }

    public static void info(String st) {
        Log.i(TAG, st);
    }

    public static void error(String st) {
        Log.e(TAG, st);
    }
}
