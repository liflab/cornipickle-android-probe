package ca.liflab.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

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
