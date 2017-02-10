package ca.liflab.sonde;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Sonde s;
    // L'identifiant de notre requête
    public final static int CHOOSE_BUTTON_REQUEST = 0;
    // L'identifiant de la chaîne de caractères qui contient le résultat de l'intent
    public final static String BUTTONS = "sdz.chapitreTrois.intent.example.Boutons";
    //Sonde s;
    final StringBuilder pro = new StringBuilder("# We define a predicate using the construct\n" +
            "# \"We say that <arguments> is/are <predicate name> when\".\n" +
            "\n" +
            "We say that $x and $y are left-aligned when (\n" +
            "  $x's left equals $y's left\n" +
            ").\n" +
            "\n" +
            "We say that $x and $y are top-aligned when (\n" +
            "  $x's top equals $y's top\n" +
            ").\n" +
            "\n" +
            "We say that the page is big when (\n" +
            "  The media query \"(min-width: 200px)\" applies\n" +
            ").\n" +
            "\n" +
            "The following rules apply when (\n" +
            "the page is big\n" +
            ").\n" +
            "\n" +
            "# We then express the fact that all menu items are either\n" +
            "# left- or top-aligned.\n" +
            "\n" +
            "\"\"\"\n" +
            "  @name Menus aligned\n" +
            "  @description All list items should either be left- or top-aligned.\n" +
            "  @severity Warning\n" +
            "\"\"\"\n" +
            "For each $z in $(.menu li) (\n" +
            "  For each $t in $(.menu li) (\n" +
            "    ($z and $t are left-aligned)\n" +
            "    Or\n" +
            "    ($z and $t are top-aligned)\n" +
            "  )\n" +
            ").\n");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button _send = (Button) findViewById(R.id.button);

        Button _btn_add = (Button) findViewById(R.id.btnAdd);

        Button _btnInterepter = (Button) findViewById(R.id.btnInterpreter);

        _btnInterepter.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  Toast toast = Toast.makeText(getApplicationContext(), "interpter " + s.interpreter, Toast.LENGTH_LONG
                                                  );
                                                  toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                                                  toast.show();
String l=s.getDataImage();
                                                  Log.d("interpret",l);
                                                  s.sendStart("http://192.168.109.1:10101/mobiletest/",l , Sonde.RequestName.image);

                                              }
                                          }

        );


        _btn_add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Toast toast = Toast.makeText(getApplicationContext(), pro.toString(), Toast.LENGTH_LONG
                                            );
                                            toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                                            toast.show();

                                            if (s != null) {
                                              //  pro.
                                                s.sendStart("http://192.168.109.1:10101/addProp/", pro.toString(), Sonde.RequestName.add);

                                            }
                                        }
                                    }

        );

        _send.setOnClickListener(new View.OnClickListener() {
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
    public void onWindowFocusChanged(boolean hasFocus) {

        // s.getHierarchyActivity();
        s = new Sonde(this);
        //   Log.d("sonde1", s.jsonObj.toString());

        // s.sendStart("http://192.168.109.1:10101/mobiletest/");
        Toast toast = Toast.makeText(getApplicationContext(), "you can send now", Toast.LENGTH_LONG
        );
        toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
        toast.show();

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
