package ca.liflab.examples;

import android.os.Bundle;
import android.view.KeyEvent;

import ca.liflab.sonde.R;

public class TouchTargetSize extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_target_size);

    }



    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        super.onKeyUp(keyCode, event);
        switch (keyCode) {

            case KeyEvent.KEYCODE_B:
                generateBug();
                return true;
            case KeyEvent.KEYCODE_N:
                returnToNormal();
                return true;

            default:
                return true;
        }
    }

    // generer un bug
    public void generateBug() {

       setContentView(R.layout.activity_touch_target_size_bug);


    }

    public void returnToNormal() {
        setContentView(R.layout.activity_touch_target_size);

    }
}
