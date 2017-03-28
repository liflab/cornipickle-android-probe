package ca.liflab.sonde;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class SondeActivity extends Activity {
    Sonde s;
    String nameFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_sonde);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        switch (keyCode) {
            /*
            case KeyEvent.KEYCODE_A:
              //  sendPropToserver(nameFile);
                return true;
            case KeyEvent.KEYCODE_B:

                return true;*/
            case KeyEvent.KEYCODE_BACK:
                Intent intent = new Intent(this, MenuListManager.class);
                startActivity(intent);

                return true;
            default:
                sendActivityUiToServer(null);
                return true;

        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (s == null || (s != null && s.acCurrent != this)) {
            s = new Sonde(this);
            sendPropToserver(nameFile);
        }
        displayTost("you can send  now prop");


    }

    protected void sendPropToserver(String nameFile) {
        this.nameFile = nameFile;

        if (s != null) {

            s.sendStart("http://192.168.109.1:10101/addProp/", readdPropFromFile(nameFile).toString(), Sonde.RequestName.add);

        }

    }

    protected void sendActivityUiToServer(MotionEvent event) {

        String l = s.getDataImage(event);
        //Log.d("interpret", l);
        s.sendStart("http://192.168.109.1:10101/mobiletest/", l, Sonde.RequestName.image);

    }

    protected void sendActivityUiToServer(View v, MotionEvent event) {

        String l = s.getDataImage(v, event);
        //Log.d("interpret", l);
        s.sendStart("http://192.168.109.1:10101/mobiletest/", l, Sonde.RequestName.image);

    }

    public void displayTost(String msg) {


        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();

    }

    public StringBuilder readdPropFromFile(String NameFile) {
        StringBuilder text = new StringBuilder();//prop
        try {
            InputStream is = getAssets().open(NameFile);
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
public  boolean customiseSend=false;
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

       // if()


        if (event.getAction() == MotionEvent.ACTION_UP) {
//if(!customiseSend)
            sendActivityUiToServer(event);
        }
        return super.dispatchTouchEvent(event);
    }
}
