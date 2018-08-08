package com.driver.login;

import android.content.Intent;;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.driver.BaseActivity;
import com.driver.R;
import com.driver.R;


public class LogoutActivity extends BaseActivity {
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();
        loginPrefsEditor.clear();
        loginPrefsEditor.commit();

        finish();
    }
}