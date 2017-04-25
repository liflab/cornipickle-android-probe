package ca.liflab.examples;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ca.liflab.sonde.R;
import ca.liflab.sonde.Sonde;


public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


    }
    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        Sonde s=new Sonde(this);
        s.getHierarchyActivity(null);
       // Log.d("sonde", s.getHierarchyActivity());
        Log.d("sonde1", s.getResultJson().toString());
    //    s.sendStart("http://192.168.2.12:10101/mobiletest/",s.jsonObj.toString(), Sonde.RequestName.autre);
     //   s.sendStart("s.sendStart(\"http://192.168.109.1:10101/mobiletest/\");
    }

    public static class MenuTabActivity extends AppCompatActivity {

        private Toolbar toolbar;
        private TabLayout tabLayout;
        private ViewPager viewPager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tab_menu);

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPager = (ViewPager) findViewById(R.id.viewpager);

            /**
             * Initializing the viewpager with fragments
             */
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFragment(MainActivity.MenuTabFragment.newInstance("Home Fragment"), "Tab 1");
            adapter.addFragment(MainActivity.MenuTabFragment.newInstance("Events Fragment"), "Tab 2");
            adapter.addFragment(MainActivity.MenuTabFragment.newInstance("Settings Fragment"), "Tab 3");

            viewPager.setAdapter(adapter);

            tabLayout = (TabLayout) findViewById(R.id.tabs);

            //adding viewpager to the tablayout
            tabLayout.setupWithViewPager(viewPager);

            /**
             *  To have icons along with text but these icons will not change on selection of the tabs.
             *  So use the selector for the icons to change.
             */

            tabLayout.getTabAt(0).setIcon(R.drawable.ic_access_time_white_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorite_white_24dp);
            // using selector for icons
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_audiotrack_white_24dp);

            /**
             * To customize the tab layout, placing image on top of the text.
             */
            //TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tabview, null);
            //tabOne.setText("Home Fragment");
            //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_home_selector, 0, 0);
            //tabLayout.getTabAt(0).setCustomView(tabOne);

        }

        class ViewPagerAdapter extends FragmentPagerAdapter {
            private final List<Fragment> mFragmentList = new ArrayList<>();
            private final List<String> mFragmentTitleList = new ArrayList<>();

            public ViewPagerAdapter(FragmentManager manager) {
                super(manager);
            }

            @Override
            public Fragment getItem(int position) {
                return mFragmentList.get(position);
            }

            @Override
            public int getCount() {
                return mFragmentList.size();
            }

            public void addFragment(Fragment fragment, String title) {
                mFragmentList.add(fragment);
                mFragmentTitleList.add(title);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentTitleList.get(position);
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_tab, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }
    }
}
