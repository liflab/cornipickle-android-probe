package ca.liflab.sonde;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // L'identifiant de notre requête
    public final static int CHOOSE_BUTTON_REQUEST = 0;
    // L'identifiant de la chaîne de caractères qui contient le résultat de l'intent
    public final static String BUTTONS = "sdz.chapitreTrois.intent.example.Boutons";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button send = (Button) findViewById(R.id.button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // handle click
                Toast toast = Toast.makeText(getApplicationContext(), "cliked",
                        Toast.LENGTH_LONG);

                toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                toast.show();

                // Le premier paramètre est le nom de l'activité actuelle
                // Le second est le nom de l'activité de destination
                Intent secondeActivite = new Intent(MainActivity.this, Main2Activity.class);


                // On rajoute un extra
              //  secondeActivite.putExtra(AGE, 31);

                // Puis on lance l'intent !
                startActivity(secondeActivite);

            }
        });


    }
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        Sonde s=new Sonde(this);
         s.getHierarchyActivity();
        Log.d("sonde1", s.jsonObj.toString());

        s.sendStart();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // On vérifie tout d'abord à quel intent on fait référence ici à l'aide de notre identifiant
        if (requestCode == CHOOSE_BUTTON_REQUEST) {
            // On vérifie aussi que l'opération s'est bien déroulée
            if (resultCode == RESULT_OK) {
                // On affiche le bouton qui a été choisi
                Toast.makeText(this, "Vous avez choisi le bouton " + data.getStringExtra(BUTTONS), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
