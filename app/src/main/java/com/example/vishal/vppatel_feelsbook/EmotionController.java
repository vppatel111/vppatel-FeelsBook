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

public class EmotionController {

    public void addEmotion(Emotion emotion) {

        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.addEmotion(emotion.getMood(), emotion.getComment());

    }

    public void updateEmotion(int id, Date date, String comment) {

        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.setEmotion(id, date, comment);
    }

    public void deleteEmotion(int id) {
        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.removeEmotion(id);
    }

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
