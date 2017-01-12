package be.ucll.mobile.aphasia.Model;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;

/**
 * Created by tompl on 12/23/2016.
 */

public class AphasiaApplication extends Application {

    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static AssetManager getAssetsManager() {
        return context.getAssets();
    }

    @Override
    public void onCreate() {
        context = getApplicationContext();
        super.onCreate();
    }

}
