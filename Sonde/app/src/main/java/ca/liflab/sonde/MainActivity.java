package ca.liflab.sonde;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Random;

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
            "# We then express the fact that all menu items are either\n" +
            "# left- or top-aligned.\n" +
            "\n" +
            "\"\"\"\n" +
            "  @name Menus aligned\n" +
            "  @description All list items should either be left- or top-aligned.\n" +
            "  @severity Warning\n" +
            "\"\"\"\n" +
            "For each $z in $(LinearLayout) (\n" +
            "For each $t in $(LinearLayout) (\n" +
            "    ($z and $t are left-aligned)\n" +
            "    Or\n" +
            "    ($z and $t are top-aligned)\n" +
    ")\n"+
            ").\n");
    final StringBuilder pro1 = new StringBuilder("# We define a predicate using the construct\n" +
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
            "\n" +
            "\n" +
            "# We then express the fact that all menu items are either\n" +
            "# left- or top-aligned.\n" +
            "\n" +
            "\"\"\"\n" +
            "  @name Menus aligned\n" +
            "  @description All list items should either be left- or top-aligned.\n" +
            "  @severity Warning\n" +
            "\"\"\"\n" +
            "For each $z in $(linearlayout button) (\n" +
            "  For each $t in $(linearlayout button) (\n" +
            "    ($z and $t are left-aligned)\n" +
            "    Or\n" +
            "    ($z and $t are top-aligned)\n" +
            "  )\n" +
            ").\n");

    Boolean _btnBugClicked = false;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button _send = (Button) findViewById(R.id.button);

        Button _btn_add = (Button) findViewById(R.id.btnAdd);

        Button _btnInterepter = (Button) findViewById(R.id.btnInterpreter);
        final Button _btnBug = (Button) findViewById(R.id.btnBug);

        _btnBug.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                _btnBugClicked = !_btnBugClicked;
                Button b = (Button) findViewById(R.id.button);
                Random r = new Random();
                int i1 = r.nextInt(500 - 20) + 20;
                b.setText(i1+ "kjhghjgkh");
                if (!_btnBugClicked) {

                //    Button b = (Button) findViewById(R.id.button);
                   b.setX(100);
                    b.setGravity(Gravity.CENTER);
                //    b.setWidth(100);
              //  Log.d("b", b.getX()+"");

                }
                else {



                    b.setX(300);
                    b.setText(String.valueOf(b.getX()));
                }
                Log.d("baaaaaaaaaaaa", b.getLeft()+" "+ b.getPaddingLeft() + "  x" +b.getX());
               // f();
            }
        });

        _btnInterepter.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                               //   onWindowFocusChanged(true);

                                               /*   Toast toast = Toast.makeText(getApplicationContext(), "interpter " + s.interpreter, Toast.LENGTH_LONG
                                                  );
                                                  toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                                                  toast.show();*/
                                                  String l = s.getDataImage();
                                                  Log.d("interpret", l);
                                                  s.sendStart("http://192.168.109.1:10101/mobiletest/", l, Sonde.RequestName.image);

                                              }
                                          }

        );


        _btn_add.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                          /*  Toast toast = Toast.makeText(getApplicationContext(), pro1.toString(), Toast.LENGTH_LONG
                                            );
                                            toast.setGravity(Gravity.CENTER | Gravity.LEFT, 0, 0);
                                            toast.show();*/

                                            if (s != null) {
                                                //  pro.
                                                s.sendStart("http://192.168.109.1:10101/addProp/", pro1.toString(), Sonde.RequestName.add);

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


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
public  void f(){


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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
