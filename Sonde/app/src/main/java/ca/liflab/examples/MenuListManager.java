package ca.liflab.examples;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import ca.liflab.sonde.R;

public class MenuListManager extends Activity{
    ListView listView1;
    String activities[]={"Bottom Navigation","Grouping Items","Text Field","Touch target size","Button Flat/Raised","List View","Button Floating","Mirroring","Menu Occurence"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_context);
        listView1=(ListView)findViewById(R.id.lstViewMenu);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,activities);
        listView1.setAdapter(adapter);
       // nameFile = "probList.txt";
        // Register the ListView  for Context menu
        // registerForContextMenu(listView1);
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                String selectedItem = (String) parent.getItemAtPosition(position);

               switch (position){


                    case 0:{
                        Intent intent = new Intent(view.getContext(), BottomNav.class);
                        startActivity(intent);
                    } break;
                    case 1:{
                        Intent intent = new Intent(view.getContext(), GoupeItemSlider.class);
                        startActivity(intent);
                    } break;
                    case 2:{
                        Intent intent = new Intent(view.getContext(), CharacterActivityCounter.class);
                        startActivity(intent);
                    } break;
                    case 3:{
                        Intent intent = new Intent(view.getContext(), TouchTargetSize.class);
                        startActivity(intent);
                    } break;
                    case 4:{
                        Intent intent = new Intent(view.getContext(), FlatActivity.class);
                        startActivity(intent);
                    } break;
                    case 5:{
                        Intent intent = new Intent(view.getContext(), SortElement.class);
                        startActivity(intent);
                    } break;
                   case 6:{
                       Intent intent = new Intent(view.getContext(), FloatingButton.class);
                       startActivity(intent);
                   } break;
                   case 7:{
                       Intent intent = new Intent(view.getContext(), RtlAcivity.class);
                       startActivity(intent);
                   } break;
                   case 8:{
                       Intent intent = new Intent(view.getContext(), Menu_occurrence.class);
                       startActivity(intent);
                   } break;
                }

                // Display the selected item text on TextView
            //    view.setText("Your favorite : " + selectedItem);
              //  registerForContextMenu(listView1);
            }
        });
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


    }

    public void returnToNormal() {

    }
View v;
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
       // s=new Sonde(this);

        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Call");//groupId, itemId, order, title
        menu.add(0, v.getId(), 0, "SMS");
        this.v=v;
      //  ViewGroup m =(ViewGroup) menu;
       // sendActivityUiToServer(m);
       // menu.getItem(0).

    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Call"){
          //  sendActivityUiToServer(v);

            Toast.makeText(getApplicationContext(),"calling code"+v.getClass().getSimpleName(),Toast.LENGTH_LONG).show();
            return  false;
        }
        else if(item.getTitle()=="SMS"){
            Toast.makeText(getApplicationContext(),"sending sms code",Toast.LENGTH_LONG).show();
        }else{
            return false;
        }
        return true;
    }
}
