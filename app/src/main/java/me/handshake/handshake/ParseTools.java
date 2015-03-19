package me.handshake.handshake;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

/**
 * Created by nathan on 3/1/15.
 */
public final class ParseTools {
    private ParseTools() {}

    public static void newUser(String name, String email, String password, String github, String linkedin, SignUpCallback cb) {
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setEmail(email);
        user.setPassword(password);
        user.put("name", name);
        user.put("github", github);
        user.put("linkedin", linkedin);
        user.signUpInBackground(cb);
    }

    public static void postHandshake(long timestamp, int compass) throws ParseException {
        ParseObject handshake = new ParseObject("Handshake");
        handshake.put("timestamp", timestamp);
        handshake.put("compass", compass);
        handshake.put("userId", ParseUser.getCurrentUser().getObjectId());
        handshake.save();
    }

    public static void findOtherHandshakes(final long timestamp, final int compass, final FindHandshakeCallback callback) {
        final ParseQuery<ParseObject> handshakeQuery = ParseQuery.getQuery("Handshake");
        final long timestampLow = timestamp - 5;
        final long timestampHigh = timestamp + 5;
        /*int compassOpposite;
        if (compass < 180) {
            compassOpposite = compass + 180;
        } else {
            compassOpposite = compass - 180;
        }
        final int compassLow = compassOpposite - 15;
        final int compassHigh = compassOpposite + 15;*/
        handshakeQuery.whereGreaterThan("timestamp", timestampLow);
        handshakeQuery.whereLessThan("timestamp", timestampHigh);
        handshakeQuery.whereNotEqualTo("userId", ParseUser.getCurrentUser().getObjectId());
        /*handshakeQuery.whereGreaterThan("compass", compassLow);
        handshakeQuery.whereLessThan("compass", compassHigh);*/
        handshakeQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject obj, ParseException e) {
                if (obj == null) {
                    // no handshake
                    LogTools.debug("no handshake found");
                } else {
                    LogTools.debug("got handshake");
                    final String userId = (String) obj.get("userId");
                    final ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("objectId", userId);
                    userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user == null) {
                                // no user
                                LogTools.debug("no user found");
                            } else {
                                LogTools.debug("got user");
                                callback.done(user);
                            }
                        }
                    });
                }
            }
        });

    }
}
