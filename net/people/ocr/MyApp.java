package net.people.ocr;

import android.app.Application;

import net.people.ocr.sec.LoginManager;

public class MyApp extends Application {

    private static final String TAG = "MyApp";

    @Override
    public void onCreate() {
        super.onCreate();

        LoginManager.main();

    }
}
