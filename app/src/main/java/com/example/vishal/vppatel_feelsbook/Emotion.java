package com.example.vishal.vppatel_feelsbook;

import java.util.Date;

public class Emotion {

    // TODO: A more intuitive way to do moods? Like store them as classes or something else. I hate
    // doing string checks.
    private String mood;
    private Date date;
    private String comment;

    // When a new emotion is added, it might have an optional comment.
    public Emotion (String mood, String comment){
        this.mood = mood;
        this.date = new Date();
        this.comment = comment;
    }

    public String getMood() {
        return this.mood;
    }

    public Date getDate() {
        return this.date;
    }

    public String getComment() {
        return this.comment;
    }

}
