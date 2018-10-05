package com.example.vishal.vppatel_feelsbook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.FileOutputStream;

// Limit comment input to 100 characters.
public class FeelsBookApplication extends Application {

    public static final String SAVE_FILE = "file.sav";

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
        super.onCreate();
    }
}
