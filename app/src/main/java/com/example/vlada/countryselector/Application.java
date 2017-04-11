package com.example.vlada.countryselector;

import android.util.Log;

public class Application extends android.app.Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    public static Application getSharedInstance(){
        return application;
    }
}
