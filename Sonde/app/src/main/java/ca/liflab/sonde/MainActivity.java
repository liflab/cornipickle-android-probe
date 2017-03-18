package ca.liflab.sonde;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Range;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class MainActivity extends Activity {
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
            ")\n" +
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
        Button btnBox = (Button) findViewById(R.id.btnStart);

        final TextView imgErr = (TextView) findViewById(R.id.imgError);
        btnBox.setOnClickListener(boutonClik);

        _btnBug.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                _btnBugClicked = !_btnBugClicked;
                Button b = (Button) findViewById(R.id.button);
                Random r = new Random();
                int i1 = r.nextInt(500 - 20) + 20;
                // b.setText(i1+ "kjhghjgkh");
                setContentView(R.layout.bottom_navigation);
                if (!_btnBugClicked) {

                    //    Button b = (Button) findViewById(R.id.button);
                    // b.setX(100);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    // params.weight = 1.0f;
                    params.gravity = Gravity.CENTER;

                    b.setLayoutParams(params);
                    imgErr.setX(100);
                    //  v.setX(300);
                    //    b.setWidth(100);
                    //  Log.d("b", b.getX()+"");

                } else {

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    // params.weight = 1.0f;
                    params.gravity = Gravity.LEFT;

                    b.setLayoutParams(params);
                    imgErr.setX(1);
                    //  b.setText(String.valueOf(b.getX()));
                }
                Log.d("baaaaaaaaaaaa", b.getLeft() + " " + b.getPaddingLeft() + "  x" + b.getX());
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
                                                s.sendStart("http://192.168.109.1:10101/addProp/", readdPr().toString(), Sonde.RequestName.add);

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

    public StringBuilder readdPr() {
        StringBuilder text = new StringBuilder();
        try {
            InputStream is = getAssets().open("prop1.txt");

            // We guarantee that the available method returns the total
            // size of the asset...  of course, this does mean that a single
            // asset can't be more than 2 gigs.
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.

            text = new StringBuilder(new String(buffer));


/*
       EditText tv = (EditText) findViewById(R.id.editTextid);
        if(tv!=null) {
            Log.d("exem", text);
            tv.setText(text);
        }
*/
        } catch (IOException e) {
            // Should never happen!
            Log.d("exception", e.toString());
        }
        return text;
    }

    /*
  * Appelée qu'à la première création d'une boîte de dialogue
  * Les fois suivantes, on se contente de récupérer la boîte de dialogue déjà créée…
  * Sauf si la méthode « onPrepareDialog » modifie la boîte de dialogue.
 */
    private final static int ENERVEMENT = 4;
    private int compteur = 0;

    private final static int ID_NORMAL_DIALOG = 0;
    private final static int ID_ENERVEE_DIALOG = 1;
    Dialog box = null;

    @Override
    public Dialog onCreateDialog(int id) {
        if (box != null)
            return box;

        switch (id) {
            // Quand on appelle avec l'identifiant de la boîte normale
            case ID_NORMAL_DIALOG:
                box = new Dialog(this);

                box.setTitle("Je viens tout juste de naître.");
                box.setContentView(R.layout.dialogfile);
                break;

            // Quand on appelle avec l'identifiant de la boîte qui s'énerve
            case ID_ENERVEE_DIALOG:
                box = new Dialog(this);
                box.setContentView(R.layout.dialogfile);
                box.setTitle("ET MOI ALORS ???");
        }
        readdPr();

        return box;
    }

    private View.OnClickListener boutonClik = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Tant qu'on n'a pas invoqué la première boîte de dialogue 5 fois
            if (compteur < ENERVEMENT) {
                //on appelle la boîte normale
                compteur++;
                showDialog(ID_NORMAL_DIALOG);
            } else
                showDialog(ID_ENERVEE_DIALOG);
            // readdPr();
            dialo = true;
        }
    };


    @Override
    public void onPrepareDialog(int id, Dialog box) {
        if (id == ID_NORMAL_DIALOG && compteur > 1)
            box.setTitle("On est au " + compteur + "ème lancement !");
        //On ne s'intéresse pas au cas où l'identifiant vaut 1, puisque cette boîte affiche le même texte à chaque lancement
    }

    boolean dialo = false;

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
        if (dialo) {

            readdPr();
        }

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
