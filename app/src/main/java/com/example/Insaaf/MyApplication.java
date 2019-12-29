package com.example.Insaaf;

import android.app.Application;

import com.example.Insaaf.util.SharedPreferenceHelper;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferenceHelper.INSTANCE.initialize(this);
    }
}
