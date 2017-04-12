package ca.liflab.sonde;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Window;

/**
 * Created by chafik on 2017-04-11.
 */

@RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class ActivityLifeCycleCallBack  implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
     Log.d("tessssssssssssss0",activity.getClass().getSimpleName());
        //on va creer event window
      Window win= activity.getWindow();
      Window.Callback localCallback = win.getCallback();
       win.setCallback(new WindowCallback(localCallback,activity));

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
