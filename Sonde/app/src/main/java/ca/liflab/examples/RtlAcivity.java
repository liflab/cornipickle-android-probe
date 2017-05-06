package ca.liflab.examples;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;

import java.util.Locale;

import ca.liflab.probe.R;

public class RtlAcivity extends SondeActivity {
public  String lang="fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_ltr_acivity);


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
            case  KeyEvent.KEYCODE_E:
                Locale locale = new Locale("en");
                Locale.setDefault(locale);
                Configuration config = getBaseContext().getResources().getConfiguration();
                config.locale = locale;
                setContentView(R.layout.activity_ltr_acivity);

            default:
                return true;
        }
    }

    // generer un bug
    public void generateBug() {

        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        setContentView(R.layout.activity_rtl_acivity_bug);
    }

    // generer un bug
    public void returnTonormal() {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = locale;
        setContentView(R.layout.activity_rtl_acivity);


    }
}
