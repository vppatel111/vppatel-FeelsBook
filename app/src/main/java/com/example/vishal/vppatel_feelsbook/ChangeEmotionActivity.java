package com.example.vishal.vppatel_feelsbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeEmotionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_emotion);

        Intent intent = getIntent();
        final long emotionId = intent.getLongExtra(DisplayHistoryActivity.ITEM_ID_MESSAGE, -1);

        if (emotionId >= 0) {

            // Initialize the date and comment fields.
            //TextView timeText = (TextView) findViewById(R.id.newTimeText);
            TextView dateText = (TextView) findViewById(R.id.newDateText);
            TextView commentText = (TextView) findViewById(R.id.newCommentText);
            EmotionList feels = FeelsBookApplication.getFeels();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String dateString = dateFormat.format(feels.getEmotion((int) emotionId).getDate());

            dateText.setText(dateString);
            commentText.setText(feels.getEmotion((int) emotionId).getComment());
        }

        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToEmotion((int) emotionId);
            }
        });

        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmotion((int) emotionId);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();

        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.loadFromFile(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
    }

    public void closeActivity() {
        this.finish();
    }

    public Date parseDateInput(String input) {
        try {
            Date newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(input);
            return newDate;
        } catch (ParseException e) {
            return null;
        }
    }

    public void deleteEmotion(int emotionId) {
        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.deleteEmotion(emotionId);
        emotionController.saveChanges(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
        closeActivity();
    }

    public void saveChangesToEmotion(int emotionId) {

        // Get changes and use EmotionController to save them
        TextView dateText = (TextView) findViewById(R.id.newDateText);
        TextView commentText = (TextView) findViewById(R.id.newCommentText);

        // Use date format to parse the input for me
        Date newDate = parseDateInput(dateText.getText().toString());

        if (newDate != null) {
            EmotionController emotionController = FeelsBookApplication.getFeelsController();
            emotionController.updateEmotion(emotionId, newDate, commentText.getText().toString());
            emotionController.saveChanges(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
            closeActivity();
        } else {
            // Show an invalid date message,
            Toast toast = Toast.makeText(this, "Invalid Date/Time", Toast.LENGTH_SHORT);
            toast.show();
        }


    }
}
