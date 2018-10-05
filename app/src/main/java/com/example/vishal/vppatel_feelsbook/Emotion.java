package com.example.vishal.vppatel_feelsbook;

import java.util.Date;

/**
 * Emotion.java
 * Purpose: This model stores information about one emotion.
 * Rationale: Need a data class to store information specific to an emotion..
 */
public class Emotion {

    private String mood;    // The type of the mood.
    private Date date;      // The date of the mood.
    private String comment; // The comment of the mood.

    // When a new emotion is added, it might have an optional comment.
    public Emotion (String mood, String comment){
        this.mood = mood;
        this.date = new Date();
        this.comment = comment;
    }

    // Returns the emotion's mood type.
    public String getMood() {
        return this.mood;
    }

    // Returns the emotion's date type.
    public Date getDate() {
        return this.date;
    }

    // Sets the emotion's date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Returns the emotion's comment.
    public String getComment() {
        return this.comment;
    }

    // Sets the emotion's comment.
    public void setComment(String comment) {
        this.comment = comment;
    }

}
