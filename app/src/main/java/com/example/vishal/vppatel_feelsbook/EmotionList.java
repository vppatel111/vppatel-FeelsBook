package com.example.vishal.vppatel_feelsbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Observable;

/**
 * EmotionList.java
 * Purpose: This model stores information about moods.
 * Rationale: Following MVC model.
 */
public class EmotionList extends Observable {

    // Stores our emotions.
    // Note: EmotionList ensures this stays sorted in chronological order.
    private ArrayList<Emotion> emotionHistory;

    // When created, make a new emotionHistory.
    public EmotionList() {
        this.emotionHistory = new ArrayList<Emotion>();
    }

    // Adds an emotion to emotionHistory.
    // Inputs: mood - The type of the emotion
    //         comment - A 100 character string comment.
    // Outputs: Adds a new emotion with parameters: mood, comment to emotionHistory.
    public void addEmotion(String mood, String comment) {
        this.emotionHistory.add(new Emotion(mood, comment));

        // Need to sort items to maintain chronological order in the case that the user manuallu
        // set the date for an emotion to something in the future.
        sortEmotions();

        setChanged();
        notifyObservers();
    }

    // Sorts emotion history in chronological order.
    // Inputs - Uses this.emotionHistory arraylist.
    // Outputs - this.emotionHistory is now sorted in chronological order.
    public void sortEmotions() {

        // Sort ArrayList with custom comparator that uses dates.
        this.emotionHistory.sort(new Comparator<Emotion>() {
            @Override
            public int compare(Emotion o1, Emotion o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

    }

    // Changes a specific emotion in this.emotionHistory()
    // Inputs: id - the index of the specific emotion
    //         date - the new date for the emotion
    //         comment - the new comment for the emotion
    // Outputs: A specific emotion with index id is changed with the new date and comment.
    public void setEmotion(int id, Date date, String comment) {

        // Get the current info for the emotion
        Emotion changeEmotion = this.emotionHistory.get(id);

        // Change the date and comment.
        changeEmotion.setDate(date);
        changeEmotion.setComment(comment);

        // Replace the existing emotion with our changed one.
        this.emotionHistory.set(id, changeEmotion);

        // Need to sort items to maintain chronological order.
        sortEmotions();

        setChanged();
        notifyObservers();
    }

    // Returns a specific emotion in emotionHistory with index id.
    public Emotion getEmotion(int id) {
        return this.emotionHistory.get(id);
    }

    // Removes a specific emotion in emotionHistory with index id.
    public void removeEmotion(int id) {
        this.emotionHistory.remove(id);

        setChanged();
        notifyObservers();
    }

    // Returns the emotionHistory arraylist.
    public ArrayList<Emotion> getEmotionHistory() {
        return this.emotionHistory;
    }

    // Replaces the entire emotionHistory with a new one.
    // Inputs: emotionHistory - the new emotionHistory.
    // Outputs: this.emotionHistory is replaced with emotionHistory.
    public void setEmotionHistory(ArrayList<Emotion> emotionHistory) {
        this.emotionHistory = emotionHistory;

        setChanged();
        notifyObservers();
    }

}
