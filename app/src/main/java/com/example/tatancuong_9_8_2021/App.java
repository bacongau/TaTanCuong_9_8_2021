package com.example.tatancuong_9_8_2021;

import android.app.Application;

public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

    }

    public static App getInstance() {

        return sInstance;
    }
}
