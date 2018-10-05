package com.example.vishal.vppatel_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add the MainActivity view as an observer to the EmotionList model.
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.addObserver(this);

        Button historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEmotionHistoryActivity();
            }
        });

        Button fearButton = (Button) findViewById(R.id.fearButton);
        fearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Fear");
            }
        });

        Button loveButton = (Button) findViewById(R.id.loveButton);
        loveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Love");
            }
        });

        Button angerButton = (Button) findViewById(R.id.angerButton);
        angerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Anger");
            }
        });

        Button sadnessButton = (Button) findViewById(R.id.sadnessButton);
        sadnessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Sadness");
            }
        });

        Button joyButton = (Button) findViewById(R.id.joyButton);
        joyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Joy");
            }
        });

        Button surpriseButton = (Button) findViewById(R.id.surpriseButton);
        surpriseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEmotion("Surprise");
            }
        });

    }

    public void onStart() {
        super.onStart();

        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.loadFromFile(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
    }

    // Packages up information received from the user and sends it to the EmotionController.
    // TODO: Constrain input size to 100 characters.
    // Print date in ISO format in the history
    public void addEmotion(String mood) {

        String comment = "";
        TextView commentText = (TextView) findViewById(R.id.commentText);

        // Check that comment text box is NOT empty.
        if (!commentText.getText().toString().equals("")) {
            comment = commentText.getText().toString();
        }

        Emotion newEmotion = new Emotion(mood, comment);

        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.addEmotion(newEmotion);
        emotionController.saveChanges(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);

    }

    // Opens the Emotion History Display activity
    public void openEmotionHistoryActivity() {
        Intent intent = new Intent(this, DisplayHistoryActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateEmotionCount((EmotionList) o); // TODO: Confirm type casting.
    }


    public void updateEmotionCount(EmotionList emotionList) {

        HashMap<String, Integer> emotionCount = new HashMap<String, Integer>();

        for (Emotion e: emotionList.getEmotionHistory()) {
            emotionCount.put(e.getMood(), 1 + emotionCount.getOrDefault(e.getMood(), 0));
        }

        // Update the count of all emotions.
        TextView fear = (TextView) findViewById(R.id.fearCountText);
        fear.setText("0");
        if (emotionCount.containsKey("Fear")) {
            fear.setText(emotionCount.get("Fear").toString());
        }

        TextView surprise = (TextView) findViewById(R.id.surpriseCountText);
        surprise.setText("0");
        if (emotionCount.containsKey("Surprise")) {
            surprise.setText(emotionCount.get("Surprise").toString());
        }

        TextView anger = (TextView) findViewById(R.id.angerCountText);
        anger.setText("0");
        if (emotionCount.containsKey("Anger")) {
            anger.setText(emotionCount.get("Anger").toString());
        }

        TextView sadness = (TextView) findViewById(R.id.sadnessCountText);
        sadness.setText("0");
        if (emotionCount.containsKey("Sadness")) {
            sadness.setText(emotionCount.get("Sadness").toString());
        }

        TextView love = (TextView) findViewById(R.id.loveCountText);
        love.setText("0");
        if (emotionCount.containsKey("Love")) {
            love.setText(emotionCount.get("Love").toString());
        }

        TextView joy = (TextView) findViewById(R.id.joyCountText);
        joy.setText("0");
        if (emotionCount.containsKey("Joy")) {
            joy.setText(emotionCount.get("Joy").toString());
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.deleteObserver(this);
    }

}
