package com.driver.navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.design.widget.NavigationView;

import com.google.gson.Gson;
import com.driver.BaseActivity;
import com.driver.R;
import com.driver.entity.Driver;
import com.driver.login.LoginActivity;
import com.driver.login.LogoutActivity;
import com.driver.order.CurrentOrderFragment;
import com.driver.userProfile.UserProfileFragment;

public class NavigationActivity extends BaseActivity implements
        NavigationView.OnNavigationItemSelectedListener, UserProfileFragment.OnFragmentInteractionListener,
        CurrentOrderFragment.OnFragmentInteractionListener{

    Driver driver;
    Bundle bundle;
    String isEnglish;
    String deliveryShift;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //check if user is login
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        boolean isLogin = loginPreferences.getBoolean("isLogin",false);
        isEnglish = loginPreferences.getString("language","");

        if(isLogin) {
            Gson gson = new Gson();
            String json = loginPreferences.getString("driver", "");
            driver = gson.fromJson(json, Driver.class);
            bundle = new Bundle();
            bundle.putParcelable("driver", driver);
            bundle.putString("language", isEnglish);
            loadFragment(CurrentOrderFragment.class);
        } else {
            Intent it = new Intent(this, LoginActivity.class);
            startActivity(it);
        }
    }

    @Override
    public void onBackPressed() {
        //fragment back button

        if(getFragmentManager().getBackStackEntryCount() > 0){
            getFragmentManager().popBackStack();
        } else{
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class fragmentClass = null;
        if (id == R.id.nav_logout) {
            Intent intent = new Intent(this,LogoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_userprofile) {
            fragmentClass = UserProfileFragment.class;
            loadFragment(fragmentClass);
        } else if (id == R.id.nav_currentorder) {
            fragmentClass = CurrentOrderFragment.class;
            loadFragment(fragmentClass);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void loadFragment(Class fragmentClass) {
        Fragment fragment = null;
        try {
            fragment = (android.support.v4.app.Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragment.setArguments(bundle);
        //Change screen to option selected (using fragments)
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flContent, fragment);
        fragmentTransaction.commit();
    }

    public void setActionBarTitle(String title){
        TextView titleTextView = findViewById(R.id.toolbar_title);
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {}

}
