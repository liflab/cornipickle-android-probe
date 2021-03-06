package ca.liflab.probe;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by chafik on 2017-04-11.
 */

@SuppressLint("NewApi")
public class ActivityLifeCycleCallBack implements Application.ActivityLifecycleCallbacks
{
        public ActivityLifeCycleCallBack(String server) {
                ProbeConfig.server=server;
        }

        public ActivityLifeCycleCallBack() {
        }


        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

         ProbeConfig.subscribeToWindow(activity);
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
