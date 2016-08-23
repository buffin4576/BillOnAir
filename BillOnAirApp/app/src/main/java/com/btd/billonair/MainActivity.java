package com.btd.billonair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


   // private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences settings = getSharedPreferences("Shared",0);
        Query.SetContext(getApplicationContext());
        if(settings.getBoolean("online",false)) {
            try {
                Query.GetAndExecAllQueries();
                Query.SendQueriesOffline();
            } catch (Exception e) {
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OneFragment(), "CONTI");
        adapter.addFragment(new TwoFragment(), "STANZE");
        adapter.addFragment(new ThreeFragment(), "MENSILE");
        adapter.addFragment(new Charts_fragment(), "GRAFICO");
        viewPager.setAdapter(adapter);
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
        SharedPreferences settings = getSharedPreferences("Shared",0);
        if(settings.getBoolean("online",false))
        {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }
        else
        {
            //getMenuInflater().inflate(R.menu.menu_main_offline, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        /*if (id == R.id.login_menu)
        {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }*/
        if(id==R.id.logout_menu)
        {
            SharedPreferences settings = getSharedPreferences("Shared",0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("online",false);
            editor.putString("username","offline");
            editor.commit();
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);

        }
        if(id==R.id.aggiungi_spesa_menu)
        {
            final Bundle bund=new Bundle();
            Intent intent = new Intent(this,AggiuntaSpesaEntrata.class);
            bund.putCharSequence("tipo","Spesa");
            intent.putExtras(bund);
            startActivity(intent);
            Log.w("menu","aggiungi_spesa_menu");
        }
        if(id==R.id.aggiungi_entrata_menu)
        {
            final Bundle bund=new Bundle();
            Intent intent = new Intent(this,AggiuntaSpesaEntrata.class);
            bund.putCharSequence("tipo","Entrata");
            intent.putExtras(bund);
            startActivity(intent);
            Log.w("menu","aggiungi_entrata_menu");
        }

        return super.onOptionsItemSelected(item);
    }

}
