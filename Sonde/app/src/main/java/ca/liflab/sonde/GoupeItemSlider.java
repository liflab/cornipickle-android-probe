package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

public class GoupeItemSlider extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goupe_item_slider);
        nameFile = "groupe_item.txt";

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

        setContentView(R.layout.activity_goupe_item_slider_bug);

    }

    // generer un bug
    public void returnTonormal() {

        setContentView(R.layout.activity_goupe_item_slider);

    }
}
