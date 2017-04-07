package ca.liflab.sonde;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

public class RtlAcivity extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rtl_acivity_bug);
        nameFile = "mirroring.txt";

    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        switch (keyCode) {

            case KeyEvent.KEYCODE_B:
                generateBug();
                return true;
            case KeyEvent.KEYCODE_N:
                returnTonormal();
                return true;

            default:
                return true;
        }
    }

    // generer un bug
    public void generateBug() {

        setContentView(R.layout.activity_rtl_acivity);
    }

    // generer un bug
    public void returnTonormal() {

        setContentView(R.layout.activity_rtl_acivity_bug);


    }
}
