package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        Sonde s=new Sonde(this);
        s.getHierarchyActivity();
       // Log.d("sonde", s.getHierarchyActivity());
        Log.d("sonde1", s.jsonObj.toString());
        s.sendStart("http://192.168.2.12:10101/mobiletest/",s.jsonObj.toString(), Sonde.RequestName.autre);
     //   s.sendStart("s.sendStart(\"http://192.168.109.1:10101/mobiletest/\");
    }
}
