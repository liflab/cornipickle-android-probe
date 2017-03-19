package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;

public class MisalinedElement extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misalined_element);

        nameFile = "prop1.txt";
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
                return super.onKeyUp(keyCode, event);
        }
    }

    // generer un bug
    public void generateBug() {

        Button b = (Button) findViewById(R.id.button);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // params.weight = 1.0f;
        params.gravity = Gravity.LEFT;
        b.setLayoutParams(params);

    }

    public void returnToNormal() {
        Button b = (Button) findViewById(R.id.button);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        // params.weight = 1.0f;
        params.gravity = Gravity.CENTER;
        b.setLayoutParams(params);
    }
}
