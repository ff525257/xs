package com.wh.core;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getApplicationContext2() {
        return context;
    }
}
