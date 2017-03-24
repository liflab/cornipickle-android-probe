package ca.liflab.sonde;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

public class SondeActivity extends Activity {
    Sonde s;
    String nameFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.activity_sonde);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_I:
                sendActivityUiToServer();
                return true;
            case KeyEvent.KEYCODE_A:
                sendPropToserver(nameFile);
                return true;
            case KeyEvent.KEYCODE_B:

                return true;

            default:
                return true;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (s == null ||  (s!=null && s.acCurrent!=this)) {
            s = new Sonde(this);
        }
        displayTost("you can send know prop");

    }

    protected void sendPropToserver(String nameFile) {
        this.nameFile = nameFile;

        if (s != null) {

            s.sendStart("http://192.168.109.1:10101/addProp/", readdPropFromFile(nameFile).toString(), Sonde.RequestName.add);

        }

    }

    protected void sendActivityUiToServer() {

        String l = s.getDataImage();
        //Log.d("interpret", l);
        s.sendStart("http://192.168.109.1:10101/mobiletest/", l, Sonde.RequestName.image);

    }
    protected void sendActivityUiToServer(View v) {

        String l = s.getDataImage(v);
        //Log.d("interpret", l);
        s.sendStart("http://192.168.109.1:10101/mobiletest/", l, Sonde.RequestName.image);

    }
    public void displayTost(String msg) {


        Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();

    }

    public StringBuilder readdPropFromFile(String NameFile) {
        StringBuilder text = new StringBuilder();//prop
        try {
            InputStream is = getAssets().open(NameFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            text = new StringBuilder(new String(buffer));

        } catch (IOException e) {
            // Should never happen!
            Log.d("exception", e.toString());
        }
        return text;
    }
}
