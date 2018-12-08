package com.example.abbes.marvelapp;

import android.app.Application;
import android.content.Context;
public class AppContext extends Application {

    // retourner le Context de l'application
    private static Context sContext;
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
