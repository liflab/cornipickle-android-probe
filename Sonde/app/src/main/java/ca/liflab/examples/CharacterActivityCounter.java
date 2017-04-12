package ca.liflab.examples;

import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.TextView;

import ca.liflab.sonde.R;
import ca.liflab.sonde.SondeConfig;

public class CharacterActivityCounter extends SondeActivity {
    TextInputLayout text1, text2;
    CharacterCounter c1, c2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.character_counter);
        text1 = (TextInputLayout) findViewById(R.id.text1);
        text2 = (TextInputLayout) findViewById(R.id.text2);
        c1 = new CharacterCounter(text1, 0, 10, (TextView) findViewById(R.id.colorLineCounter1));
        c2 = new CharacterCounter(text2, 0, 80, (TextView) findViewById(R.id.colorLineCounter2));
        text1.getEditText().addTextChangedListener(c1);
        text2.getEditText().addTextChangedListener(c2);

        SondeConfig.setPosLayoutResult(SondeConfig.PosLayoutResult.right_top);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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
        c1.inverse = true;


    }

    // generer un bug
    public void returnTonormal() {
        c1.inverse = false;
        setContentView(R.layout.character_counter);
        text1 = (TextInputLayout) findViewById(R.id.text1);
        text2 = (TextInputLayout) findViewById(R.id.text2);
        c1 = new CharacterCounter(text1, 0, 10, (TextView) findViewById(R.id.colorLineCounter1));
        c2 = new CharacterCounter(text2, 0, 80, (TextView) findViewById(R.id.colorLineCounter2));
        text1.getEditText().addTextChangedListener(c1);
        text2.getEditText().addTextChangedListener(c2);


    }
}