package ca.liflab.examples;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;

import ca.liflab.probe.R;

public class FlatActivity extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);
        returnTonormal();
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

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setBackgroundColor(Color.TRANSPARENT);
        Button btn6 = (Button) findViewById(R.id.button6);
        btn6.setBackgroundColor(Color.TRANSPARENT);
        //  btn6.getForeground().co
        Button btn8 = (Button) findViewById(R.id.button8);
        btn8.setBackgroundColor(Color.TRANSPARENT);
    }

    // generer un bug
    public void returnTonormal() {

        Button btn3 = (Button) findViewById(R.id.button3);
        btn3.setBackgroundColor(Color.BLUE);
        Button btn6 = (Button) findViewById(R.id.button6);
        btn6.setBackgroundColor(Color.BLUE);
        Button btn8 = (Button) findViewById(R.id.button8);
        btn8.setBackgroundColor(Color.BLUE);


    }
}
