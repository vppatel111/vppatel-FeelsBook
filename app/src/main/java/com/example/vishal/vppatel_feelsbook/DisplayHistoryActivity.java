package com.example.vishal.vppatel_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DisplayHistoryActivity extends AppCompatActivity implements Observer {

    public static final String ITEM_ID_MESSAGE = "com.example.vishal.vppatel_feelsbook.ITEM_ID_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_history);

        // Add the DisplayHistoryActivity view as an observer to the EmotionList model.
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.addObserver(this);

        ListView emotionsHistoryList = (ListView) findViewById(R.id.emotionsList);
        emotionsHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openChangeEmotion(id);
            }
        });
    }

    // Note: id should correspond with model arrangement of emotions.
    public void openChangeEmotion(long id) {
        Intent intent = new Intent(this, ChangeEmotionActivity.class);
        intent.putExtra(ITEM_ID_MESSAGE, id);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EmotionList feels = FeelsBookApplication.getFeels();
        updateHistoryList(feels);
    }

    @Override
    public void update(Observable o, Object arg) {
        updateHistoryList((EmotionList) o); // TODO: Confirm type casting.
    }

    public String[] convertEmotionToArray(EmotionList emotionList) {
        ArrayList<String> emotions = new ArrayList<String>();

        for (Emotion e : emotionList.getEmotionHistory()) {
            emotions.add(e.getDate() + " - " + e.getMood() + ": " + e.getComment());
        }

        return emotions.toArray(new String[emotions.size()]);
    }

    public void updateHistoryList(EmotionList emotionList) {

        ListView emotionsHistoryList = (ListView) findViewById(R.id.emotionsList);

        String[] emotions = convertEmotionToArray(emotionList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item, emotions);
        emotionsHistoryList.setAdapter(adapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EmotionList feels = FeelsBookApplication.getFeels();
        feels.deleteObserver(this);
    }
}
