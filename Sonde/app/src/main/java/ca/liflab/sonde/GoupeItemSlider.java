package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class GoupeItemSlider extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goupe_item_slider);
        nameFile = "groupe_item.txt";

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {


            //  event.get
          //  View v = getCurrentFocus();


                //System.out.println("TOUCH DOWN!"+v.getClass().getSimpleName());
        }
        return super.dispatchTouchEvent(event);
    }
    private boolean isViewContains(View view, int eventX, int eventY) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        int width = view.getWidth();
        int height = view.getHeight();
        return eventX < x || eventX > x + width || eventY < y || eventY > y + height;
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
