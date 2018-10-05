package com.example.vishal.vppatel_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * DisplayHistoryActivity.java
 * Purpose: This activity (view) contains functions for displaying a history of emotions in a ListView.
 *
 * Rationale: A separate activity to display history reduces clutter on MainActivity.
 */
public class DisplayHistoryActivity extends AppCompatActivity implements Observer {

    // The name of the ITEM_ID message.
    public static final String ITEM_ID_MESSAGE = "com.example.vishal.vppatel_feelsbook.ITEM_ID_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);

        // Add the DisplayHistoryActivity view as an observer to the EmotionList model.
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.addObserver(this);

        // Add a listener for individual items clicked on the history ListView.
        ListView emotionsHistoryList = (ListView) findViewById(R.id.emotionsList);
        emotionsHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openChangeEmotion(id);
            }
        });

    }

    // Starts an activity to change the clicked emotion.
    // Inputs: id - the index of the emotion to change.
    public void openChangeEmotion(long id) {
        Intent intent = new Intent(this, ChangeEmotionActivity.class);
        intent.putExtra(ITEM_ID_MESSAGE, id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Load emotions from file
        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.loadFromFile(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);

        // Add the emotions into ListView.
        EmotionList feels = FeelsBookApplication.getFeels();
        updateHistoryList(feels);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateHistoryList((EmotionList) o);
    }

    // Converts an ArrayList<Emotion> to a String array of emotions.
    // Inputs: emotionList - The emotionList to convert to a String[].
    public String[] convertEmotionToArray(EmotionList emotionList) {
        ArrayList<String> emotions = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Get the details of each emotion and put it into a string, add this string into the
        // new string array.
        for (Emotion e : emotionList.getEmotionHistory()) {

            String dateString = dateFormat.format(e.getDate());
            emotions.add(dateString + " - " + e.getMood() + ": " + e.getComment());
        }

        return emotions.toArray(new String[emotions.size()]);
    }

    // Updates the ListView with emotions from the model.
    // Inputs: emotionList - A reference to the model.
    // Outputs: Creates items for each emotion in the model.
    public void updateHistoryList(EmotionList emotionList) {

        ListView emotionsHistoryList = (ListView) findViewById(R.id.emotionsList);

        // Use an adapter to add items into the ListView.
        String[] emotions = convertEmotionToArray(emotionList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, emotions);
        emotionsHistoryList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        // Unsubscribe from the model.
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.deleteObserver(this);
    }
}
