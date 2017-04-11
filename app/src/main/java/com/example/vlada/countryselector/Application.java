package com.example.vlada.countryselector;

import android.util.Log;

/**
 * Created by vlada on 10.04.2017.
 */

public class Application extends android.app.Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        Log.d("onCreate Application", "begin");
        Log.d("onCreate Application", "end");
    }

    public static Application getSharedInstance(){
        return application;
    }
}
