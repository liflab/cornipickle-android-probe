package ca.liflab.examples;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.Build;

import ca.liflab.sonde.ActivityLifeCycleCallBack;
import ca.liflab.sonde.R;


public class MyApplication extends Application {

    @Override
    public void onCreate() {

        //superier a 14
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            registerActivityLifecycleCallbacks(new ActivityLifeCycleCallBack(getResources().getString(R.string.sonde_server)));
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
