package ca.liflab.examples;

import android.app.Application;
import android.os.Build;

import ca.liflab.sonde.ActivityLifeCycleCallBack;

/**
 * Created by chafik on 2017-04-11.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifeCycleCallBack());
        }
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
