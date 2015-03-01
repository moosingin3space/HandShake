package me.handshake.handshake;

import android.content.Context;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

/**
 * Created by nathan on 3/1/15.
 */
public final class PebbleTools {
    private static final int KEY_TIMESTAMP = 5;
    private static final int KEY_COMPASS = 6;
    private static final UUID WATCHAPP_UUID = UUID.fromString("04444c59-e7a5-48ad-a145-4de0a5f833ce");
    private static boolean isWatchAppRunning = false;

    private PebbleTools() {}

    public static void registerHandshakeHandler(final Context ctx, final FindHandshakeCallback callback) {
        PebbleKit.registerReceivedDataHandler(ctx, new PebbleKit.PebbleDataReceiver(WATCHAPP_UUID) {
            public void receiveData(Context context, int transactionId, PebbleDictionary data) {
                PebbleKit.sendAckToPebble(context, transactionId);
                LogTools.info("received data from pebble " + data.size());

                long timestamp = data.getInteger(KEY_TIMESTAMP);
                int compass = data.getInteger(KEY_COMPASS).intValue();

                ParseTools.postHandshake(timestamp, compass);
                ParseTools.findOtherHandshakes(timestamp, compass, callback);
            }
        });
    }

    public static void toggleAppRunning(final Context ctx) {
        if (isWatchAppRunning) {
            PebbleKit.startAppOnPebble(ctx, WATCHAPP_UUID);
        } else {
            PebbleKit.closeAppOnPebble(ctx, WATCHAPP_UUID);
        }
        isWatchAppRunning = !isWatchAppRunning;
    }
}
