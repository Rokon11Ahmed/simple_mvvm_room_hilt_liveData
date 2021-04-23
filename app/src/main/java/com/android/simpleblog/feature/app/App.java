package com.android.simpleblog.feature.app;

import android.app.Application;
import android.content.Context;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class App extends Application {
    private  static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = (App) getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
