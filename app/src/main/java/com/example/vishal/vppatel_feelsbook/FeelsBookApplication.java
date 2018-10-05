package com.example.vishal.vppatel_feelsbook;

import android.app.Application;
import android.util.Log;

public class FeelsBookApplication extends Application {

    // Singleton
    private static EmotionList feels = null;
    static EmotionList getFeels() {
        if (feels == null) {
            feels = new EmotionList();
        }
        return feels;
    }


    // 'nother singleton
    private static EmotionController feelsController = null;
    static EmotionController getFeelsController() {
        if (feelsController == null) {
            feelsController = new EmotionController();
        }
        return feelsController;
    }

    @Override
    public void onCreate() {
        Log.d("TEST1", "onCreate: I hath been created");
        super.onCreate();
    }
}
