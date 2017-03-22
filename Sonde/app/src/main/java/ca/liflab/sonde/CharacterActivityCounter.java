package ca.liflab.sonde;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CharacterActivityCounter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiline_character_conter);
        TextInputLayout text1 = (TextInputLayout) findViewById(R.id.text1);
        TextInputLayout text2 = (TextInputLayout) findViewById(R.id.text2);

        text1.getEditText().addTextChangedListener(new CharacterCounter(text1, 0, 10, (TextView)findViewById(R.id.txtCounter)));
        text2.getEditText().addTextChangedListener(new CharacterCounter(text2, 5, 10,(TextView)findViewById(R.id.txtCounter)));
    }
}
