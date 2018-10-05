package com.example.vishal.vppatel_feelsbook;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

/**
 * EmotionController.java
 * This controller contains functions for updating the EmotionList model.
 *
 * Rationale: Following MVC model.
 */
public class EmotionController {

    // Gets the EmotionList model and adds an emotion to it.
    // Inputs: emotion - the emotion to add.
    // Outputs: Calls addEmotion() on EmotionList.
    public void addEmotion(Emotion emotion) {

        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.addEmotion(emotion.getMood(), emotion.getComment());

    }

    // Gets the EmotionList model and changes a specific emotion.
    // Inputs: id - The index of the specific emotion
    //         date - The new date.
    //         comment - The new comment.
    // Outputs: Calls setEmotion() on EmotionList.
    public void updateEmotion(int id, Date date, String comment) {

        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.setEmotion(id, date, comment);
    }

    // Gets the EmotionList model and deletes a specific emotion from it.
    // Inputs: id - The index of the specific emotion
    // Outputs: Calls removeEmotion() on EmotionList.
    public void deleteEmotion(int id) {
        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.removeEmotion(id);
    }

    // Credit to joshua2ua: lonelyTwitter.
    // Date Accessed Last: Oct 5, 2018
    // Link: https://github.com/joshua2ua/lonelyTwitter
    // Used the saving and loading from file features.
    // License: No License.md file specified.

    // Saves the EmotionList model to a file using Json/Gson.
    // Inputs: context - The context of the activity calling saveChanges().
    //         filename - the file to which to save our model.
    // Outputs: The EmotionList model is saved to a file in Gson format.
    public void saveChanges(Context context, String filename) {

        EmotionList emotionList = FeelsBookApplication.getFeels();

        try {
            FileOutputStream fos = context.openFileOutput(filename, 0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);

            Gson gson = new Gson();
            gson.toJson(emotionList.getEmotionHistory(), writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // Loads a EmotionList model from a file.
    // Inputs: context - The context of the activity calling saveChanges().
    //         filename - the file to which to save our model.
    // Outputs: Sets the current model to the one loaded
    public void loadFromFile(Context context, String filename) {

        EmotionList emotionList = FeelsBookApplication.getFeels();

        try {

            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            Gson gson = new Gson();

            //We create a 'token' to tell java what to convert to
            Type typeListEmotions = new TypeToken<ArrayList<Emotion>>(){}.getType();
            ArrayList<Emotion> newHistory = gson.fromJson(reader, typeListEmotions);
            emotionList.setEmotionHistory(newHistory);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
