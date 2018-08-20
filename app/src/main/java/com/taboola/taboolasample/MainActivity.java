package com.taboola.taboolasample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.taboola.taboolasample.fragments.ConfigFragment;
import com.taboola.taboolasample.fragments.DynamicSampleFragment;
import com.taboola.taboolasample.fragments.EndlessFeedSampleFragment;
import com.taboola.taboolasample.fragments.ListViewSampleFragment;
import com.taboola.taboolasample.fragments.RecyclerViewSampleFragment;
import com.taboola.taboolasample.fragments.XmlSampleFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_main, new XmlSampleFragment());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentToOpen;

        switch (id) {
            case R.id.xml_sample:
                fragmentToOpen = new XmlSampleFragment();
                break;
            case R.id.dynamic_sample:
                fragmentToOpen = new DynamicSampleFragment();
                break;
            case R.id.configurable_page:
                fragmentToOpen = new ConfigFragment();
                break;

            case R.id.endless_feed_sample:
                fragmentToOpen = new EndlessFeedSampleFragment();
                break;

            case R.id.rv_sample:
                fragmentToOpen = new RecyclerViewSampleFragment();
                break;

            case R.id.lv_sample:
                fragmentToOpen = new ListViewSampleFragment();
                break;

            default:
                return false;
        }

        transaction.replace(R.id.container_main, fragmentToOpen);
        transaction.commit();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}