package ca.liflab.examples;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import ca.liflab.probe.R;
import ca.liflab.probe.ProbeConfig;

public class BottomNav extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ProbeConfig.setPosLayoutResult(ProbeConfig.PosLayoutResult.right_top);

        setContentView(R.layout.bottom_navigation);

       // nameFile = "FloatingButton.txt";
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        RelativeLayout layout=(RelativeLayout)findViewById(R.id.ex);
        layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){

            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_favorites:

                            case R.id.action_schedules:

                            case R.id.action_music:

                        }
                        return true;
                    }
                });
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

        setContentView(R.layout.bottom_navigation_two_item);

    }

    public void returnToNormal() {
        setContentView(R.layout.bottom_navigation);
      BottomNavigationView  m=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        Menu m1=m.getMenu();
        for(int i=0;i<m1.size();i++){

            Log.d("menu",m1.getItem(i).toString() );
        }
    }
}
