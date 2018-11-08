package com.kenjin.submission4.Ui.Activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.kenjin.submission4.R;
import com.kenjin.submission4.Ui.Fragment.UpComingFragment;
import com.kenjin.submission4.Ui.Fragment.NowPlayingFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //FragmentManager fm = getSupportFragmentManager();
    ViewPager viewpager;
    TabLayout tab;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewpager = findViewById(R.id.viewpager);
        tab = findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText(getString(R.string.play)));
        tab.addTab(tab.newTab().setText(getString(R.string.cooming)));
        //tab.addTab(tab.newTab().setText("tabs3"));
        PagerAdapter adapter = new TabAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            startActivity(new Intent(MainActivity.this, MovieActivity.class));
        } else if (id == R.id.nav_playing) {
            getSupportActionBar().setTitle(getString(R.string.play));
            TabLayout.Tab tabs = tab.getTabAt(0);
            tabs.select();

        } else if (id == R.id.nav_cooming) {
            getSupportActionBar().setTitle(getResources().getString(R.string.cooming));
            TabLayout.Tab tabs = tab.getTabAt(1);
            tabs.select();
        }else  if(id==R.id.nav_favorit){
            startActivity(new Intent(MainActivity.this, FavoritActivity.class));
        } else if (id == R.id.nav_setting) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class TabAdapter extends FragmentPagerAdapter {
        public TabAdapter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position) {
                case 0:
                    fragment = new NowPlayingFragment();
                    break;
                case 1:
                    fragment = new UpComingFragment();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
