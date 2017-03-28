package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


/**
 * Created by Florence on 22/03/2017.
 */

public class SortElement extends SondeActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sort_element);

        nameFile = "listview_sort.txt";

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array values to show in ListView
        String[] values = new String[] { "Trevor Hansen",
                "Peter Carlsson",
                "Mary Johnson",
                "Janet Perkins",
                "Hailey Davis",
                "Matthew Williams",
                "Derek Wilson",
                "Paige Brown"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
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

        String[] values = new String[] { "Trevor Hansen",
                "Peter Carlsson",
                "Mary Johnson",
                "Janet Perkins",
                "Hailey Davis",
                "Matthew Williams",
                "Derek Wilson",
                "Paige Brown"
        };


        ListView li = (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }

    public void returnToNormal() {

        String[] values = new String[] { "Derek Wilson",
                "Hailey Davis",
                "Janet Perkins",
                "Mary Johnson",
                "Matthew Williams",
                "Paige Brown",
                "Peter Carlsson",
                "Trevor Hansen"
        };

        ListView li = (ListView) findViewById(R.id.list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
    }


}
