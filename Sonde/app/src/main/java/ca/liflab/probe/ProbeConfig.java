package ca.liflab.probe;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by Liflab
 */

public class ProbeConfig {


    protected Activity a;

    public Probe s;

    public String nameFile = "";


    static String server = "";

    final String urlInterperation = "/imageMobile/";

    final String urlProP = "/addMobile";

    public String getUrlProP() {
        return getServer() + urlProP;

    }

    public String getUrlInterperation() {
        return getServer() + urlInterperation;

    }

    public static String getServer() {

        //if(server=="")
      //  server = a.getResources().getString(R.string.sonde_server);
        return server;
    }
    public static void setServer(String ser) {


         server=ser;
    }

    private static ArrayList<Integer> posLayoutResult = new ArrayList<Integer>();

    public enum PosLayoutResult {
        right_top,
        right_bottom,
        left_top,
        left_bottom;
    }

    public String getNameActivity() {

        return this.a.getClass().getSimpleName();
    }
    public static void subscribeToWindow(Activity activity){

        //on va creer event window
        Window win = activity.getWindow();
        Window.Callback localCallback = win.getCallback();
        win.setCallback(new WindowCallback(localCallback, activity));

    }
    protected boolean isContinue() {


        try {
            if (Arrays.asList(a.getResources().getAssets().list("")).contains(nameFile))
                return true;
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

    }

    public void _OnWindowChanged() {
        if (isContinue()) {


            if ((s == null || (s != null && s.acCurrent != a)) && posLayoutResult.size() == 0) {
                s = new Probe(a);
                sendPropToserver(nameFile);

            } else if (s == null || (s != null && s.acCurrent != a)) {
                s = new Probe(a, posLayoutResult);

                sendPropToserver(nameFile);
            }
            //  displayTost("you can send  now prop");
        }

    }

    public void _dispatchTouchEvent(MotionEvent event) {
        if (isContinue() && !(s == null || !s.propAdded))

        {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                sendActivityUiToServer(event);
            }
        }

    }

    protected void sendPropToserver(String nameFile) {
        this.nameFile = nameFile;
        _OnWindowChanged();
        if (s != null && !s.propAdded && !s.propSending && isContinue()) {

            try {
                s.propSending = true;
                //    displayTost("you can send  now prop");
                s.sendStart(getUrlProP(), URLEncoder.encode(readdPropFromFile(nameFile).toString(), "UTF-8"), Probe.RequestName.add);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

    }

    protected void sendActivityUiToServer(MotionEvent event) {

        if (!(s == null || !s.getPropAdded()) && isContinue()) {

            String l = s.getDataImage(event);

            //Log.d("interpret", l);
            s.sendStart(getUrlInterperation(), l, Probe.RequestName.image);
        } else {


            sendPropToserver(nameFile);
        }


    }

    protected void sendActivityUiToServer(View v, MotionEvent event) {
        if (!(s == null || !s.propAdded) && isContinue()) {
            String l = s.getDataImage(v, event);
            //Log.d("interpret", l);
            s.sendStart(getUrlInterperation(), l, Probe.RequestName.image);
        } else {


            sendPropToserver(nameFile);
        }
    }


    public static void setPosLayoutResult(PosLayoutResult pos) {
        String p = pos.toString();
        switch (p) {
            case "right_top": {
                posLayoutResult.clear();
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_TOP);
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            break;
            case "right_Bottom": {
                posLayoutResult.clear();
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_BOTTOM);
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            break;
            case "left_top": {
                posLayoutResult.clear();
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_TOP);
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_LEFT);
            }
            break;
            case "left_Bottom": {
                posLayoutResult.clear();
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_BOTTOM);
                posLayoutResult.add(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            break;
        }
    }

    public void displayTost(String msg) {


        Toast toast = Toast.makeText(a.getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();

    }

    public StringBuilder readdPropFromFile(String NameFile) {
        StringBuilder text = new StringBuilder();//prop
        try {
            InputStream is = a.getAssets().open(NameFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            text = new StringBuilder(new String(buffer));

        } catch (IOException e) {
            // Should never happen!
            Log.d("exception", e.toString());
        }
        return text;
    }

}
