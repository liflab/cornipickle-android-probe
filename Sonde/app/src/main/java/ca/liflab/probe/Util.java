package ca.liflab.probe;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;

/**
 * Created by chafik on 2016-12-08.
 */
public class Util {

    public static void customViewBorder(View v,  int borderColor)
    {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] { 8, 8, 8, 8, 0, 0, 0, 0 });
       // shape.setColor(backgroundColor);
        shape.setStroke(4, borderColor);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(shape);
        }
        else   v.setBackgroundDrawable(shape);
    }

    public static float getAspectRatio(Activity acCurrent) {

        DisplayMetrics metrics = new DisplayMetrics();
        acCurrent.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float ratio = ((float) metrics.heightPixels / (float) metrics.widthPixels);
        return ratio;

    }

    public static String getLangue(Activity ac) {

        Configuration f = ac.getBaseContext().getResources().getConfiguration();
        return f.locale.toString();

    }

    public static String getScreenOrientation(Activity acCurrent) {
        Display getOrient = acCurrent.getWindowManager().getDefaultDisplay();

        String orientation = "portrait";
        //noinspection deprecation
        if (getOrient.getWidth() == getOrient.getHeight()) {
            //noinspection deprecation
            orientation = "Square";
        } else {
            if (getOrient.getWidth() < getOrient.getHeight()) {
                orientation = "portrait";
            } else {
                orientation = "landscape";
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

    public static float getAbsoluteBottom(Activity ac, View v) {


        float height = v.getHeight();
        float bottom = getHeight(ac) - (getAbsoluteTop(v) + height);
        return bottom;
    }

    public static float getAbsoluteRight(Activity ac, View v) {

        float width = v.getWidth();
        float right = getWidth(ac) - (getAbsoluteLeft(v) + width);


        return right;
    }

    public static int pxToDp(int px, Activity ac) {
        DisplayMetrics displayMetrics = ac.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static float getDensity(Activity ac) {

        DisplayMetrics displayMetrics = ac.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
