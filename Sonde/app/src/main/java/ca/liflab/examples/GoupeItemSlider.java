package ca.liflab.examples;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import ca.liflab.probe.R;
import ca.liflab.probe.ProbeConfig;

import static android.view.Gravity.CENTER;
import static ca.liflab.probe.R.style.TextAppearance_AppCompat;

public class GoupeItemSlider extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goupe_item_slider);
        ProbeConfig.setPosLayoutResult(ProbeConfig.PosLayoutResult.right_top);

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

        setContentView(R.layout.activity_goupe_item_slider_bug1);
        RelativeLayout r=(RelativeLayout) findViewById(R.id.rl1);
        TextView txtS3 = new TextView(this);
        txtS3.setGravity(CENTER);
        txtS3.setText("70");
        LayoutParams layout = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layout.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layout.addRule(RelativeLayout.ALIGN_PARENT_START);
        layout.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layout.setMargins(0,221,0,0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            txtS3.setTextAppearance(TextAppearance_AppCompat);
        }
        txtS3.setLayoutParams(layout);
        txtS3.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        txtS3.setTypeface(null, Typeface.BOLD);


        r.addView(txtS3);


    }

    // generer un bug
    public void returnTonormal() {

        setContentView(R.layout.activity_goupe_item_slider);

    }
}
