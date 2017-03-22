package ca.liflab.sonde;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MenuContext extends AppCompatActivity {
    ListView listView1;
    String contacts[]={"it1","ite2","i3","i4","i5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_context);
        listView1=(ListView)findViewById(R.id.lstViewMenu);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
        listView1.setAdapter(adapter);
    }
}
