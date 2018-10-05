package com.example.vishal.vppatel_feelsbook;

import android.app.Application;
import android.content.Context;
import android.util.Log;

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
import java.util.Comparator;
import java.util.Date;
import java.util.Observable;

public class EmotionList extends Observable {

    // Sorted in chronological order.
    private ArrayList<Emotion> emotionHistory;

    public EmotionList() {
        this.emotionHistory = new ArrayList<Emotion>();
    }

    // New emotions are guaranteed to be the most recent, therefore sorting after adding them
    // is not necessary.
    public void addEmotion(String mood, String comment) {
        this.emotionHistory.add(new Emotion(mood, comment));

        setChanged();
        notifyObservers();
    }

    // Sorts emotion history by date.
    public void sortEmotions() {

        this.emotionHistory.sort(new Comparator<Emotion>() {
            @Override
            public int compare(Emotion o1, Emotion o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

    }

    public void setEmotion(int id, Date date, String comment) {

        Emotion changeEmotion = this.emotionHistory.get(id);

        changeEmotion.setDate(date);
        changeEmotion.setComment(comment);

        this.emotionHistory.set(id, changeEmotion);

        // Need to sort items to maintain chronological order.
        sortEmotions();

        setChanged();
        notifyObservers();
    }

    public Emotion getEmotion(int id) {
        return this.emotionHistory.get(id);
    }

    public void removeEmotion(int id) {
        this.emotionHistory.remove(id);

        setChanged();
        notifyObservers();
    }

    public ArrayList<Emotion> getEmotionHistory() {
        return this.emotionHistory;
    }

    public void setEmotionHistory(ArrayList<Emotion> emotionHistory) {
        this.emotionHistory = emotionHistory;

        setChanged();
        notifyObservers();
    }

}
