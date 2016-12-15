package be.ucll.mobile.aphasia.Model;

/**
 * Created by Sebboz on 15-Dec-16.
 */
import android.util.Log;

public class AppLog {
    private static final String APP_TAG = "AudioRecorder";

    public static int logString(String message) {
        return Log.i(APP_TAG, message);
    }
}