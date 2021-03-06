package com.driver;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.driver.locale.MyContextWrapper;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyContextWrapper.wrap(newBase));
    }
}