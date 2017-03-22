package ca.liflab.sonde;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;

public class BottomNav extends SondeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_menu);
        nameFile = "prop2.txt";
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
