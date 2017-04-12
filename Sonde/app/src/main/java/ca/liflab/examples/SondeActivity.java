package ca.liflab.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.liflab.examples.MenuListManager;
import ca.liflab.sonde.Sonde;

public class SondeActivity extends Activity {


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
               // sendActivityUiToServer(null);
                return true;

        }
    }











    public boolean customiseSend = false;



}
