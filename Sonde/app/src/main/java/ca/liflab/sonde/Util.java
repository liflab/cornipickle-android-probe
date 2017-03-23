package ca.liflab.sonde;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

/**
 * Created by chafik on 2016-12-08.
 */
public class Util {

    public static float getAspectRatio(Activity acCurrent) {

        DisplayMetrics metrics = new DisplayMetrics();
        acCurrent.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float ratio = ((float) metrics.heightPixels / (float) metrics.widthPixels);
        return ratio;

    }

    public static int getScreenOrientation(Activity acCurrent) {
        Display getOrient = acCurrent.getWindowManager().getDefaultDisplay();

        int orientation = Configuration.ORIENTATION_UNDEFINED;
        //noinspection deprecation
        if (getOrient.getWidth() == getOrient.getHeight()) {
            //noinspection deprecation
            orientation = Configuration.ORIENTATION_SQUARE;
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = Configuration.ORIENTATION_PORTRAIT;
            } else {
                orientation = Configuration.ORIENTATION_LANDSCAPE;
            }
        }
        return orientation;
    }

    public static float getWidth(Activity acCurrent) {


        DisplayMetrics metrics = new DisplayMetrics();
        acCurrent.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
        //int deviceHeight = displayMetrics.heightPixels;
    }

    public static float getHeight(Activity acCurrent) {


        DisplayMetrics metrics = new DisplayMetrics();
        acCurrent.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public static float getAbsoluteLeft(View v) {


        int[] location = new int[2];
        v.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        return x;
    }

    public static float getAbsoluteTop(View v) {


        int[] location = new int[2];
        v.getLocationInWindow(location);
        int x = location[0];
        int y = location[1];
        return y;
    }
    public static float getAbsoluteBottom(Activity ac,View v) {


        float height = v.getHeight();
        float bottom = getHeight(ac) - (getAbsoluteTop(v) + height);
        return bottom;
    }
    public static float getAbsoluteRight(Activity ac, View v) {

        float width = v.getWidth();
        float right = getWidth(ac) - (getAbsoluteLeft(v) + width);


        return right;
    }


}
