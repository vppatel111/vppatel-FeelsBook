package com.example.vishal.vppatel_feelsbook;

import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;

public class EmotionList extends Observable {

    // Sorted in chronological order.
    private ArrayList<Emotion> emotionHistory;

    public EmotionList() {
        emotionHistory = new ArrayList<Emotion>();
    }

    // New emotions are guaranteed to be the most recent, therefore sorting after adding them
    // is not necessary.
    public void addEmotion(String mood, String comment) {
        emotionHistory.add(new Emotion(mood, comment));
        setChanged();
        notifyObservers();
    }


    public ArrayList<Emotion> getEmotionHistory() {
        return emotionHistory;
    }



}
