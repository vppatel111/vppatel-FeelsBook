package com.example.vishal.vppatel_feelsbook;

public class EmotionController {

    public void addEmotion(Emotion emotion) {

        EmotionList emotionList = FeelsBookApplication.getFeels();
        emotionList.addEmotion(emotion.getMood(), emotion.getComment());

    }
}
