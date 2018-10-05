package com.example.vishal.vppatel_feelsbook;

import android.app.Application;

/**
 * FeelsBookApplication.java
 * The FeelsBookApplication handles creating singletons of the model and controller which can be
 * accessed by all views.
 *
 * Rationale: Following MVC model.
 */
public class FeelsBookApplication extends Application {

    // The name of the file where our model: EmotionList will be saved.
    public static final String SAVE_FILE = "file.sav";

    // Credit to orezpraw: FillerCreepExample.
    // Date Accessed Last: Oct 5, 2018
    // Link: https://github.com/orezpraw/FillerCreepForAndroid
    // Used FillerCreepApplication.java as a reference
    // License: No License.md file specified.

    // Singleton for the model.
    private static EmotionList feels = null;
    static EmotionList getFeels() {
        if (feels == null) {
            feels = new EmotionList();
        }

        return feels;
    }


    // 'nother singleton for the controller.
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
