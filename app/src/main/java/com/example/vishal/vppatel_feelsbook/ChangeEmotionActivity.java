package com.example.vishal.vppatel_feelsbook;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

        initUI((int) emotionId);


    }

    // Initializes the UI elements.
    public void initUI(final int emotionId) {
        if (emotionId >= 0) {

            // Initialize the date and comment fields with the current emotion's information.
            TextView dateText = (TextView) findViewById(R.id.newDateText);
            TextView commentText = (TextView) findViewById(R.id.newCommentText);
            ImageView emotionPic = (ImageView) findViewById(R.id.emotionImage);
            EmotionList feels = FeelsBookApplication.getFeels();

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = dateFormat.format(feels.getEmotion(emotionId).getDate());
            String mood = feels.getEmotion(emotionId).getMood();

            dateText.setText(dateString);
            commentText.setText(feels.getEmotion(emotionId).getComment());

            // Set the ImageView to the selected mood type.
            if (mood.equals("Love")) {
                emotionPic.setImageResource(R.drawable.love);
            } else if (mood.equals("Joy")) {
                emotionPic.setImageResource(R.drawable.joy);
            } else if (mood.equals("Surprise")) {
                emotionPic.setImageResource(R.drawable.surprise);
            } else if (mood.equals("Anger")) {
                emotionPic.setImageResource(R.drawable.anger);
            } else if (mood.equals("Fear")) {
                emotionPic.setImageResource(R.drawable.fear);
            } else if (mood.equals("Sadness")) {
                emotionPic.setImageResource(R.drawable.sadness);
            }

        }

        // Adds an onclick listener to saveChangesToEmotion()
        Button saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChangesToEmotion(emotionId);
            }
        });

        // Adds an onclick listener to close the activity when quitting.
        Button cancelButton = (Button) findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActivity();
            }
        });

        // Adds an onclick listener to delete the emotion.
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmotion((emotionId));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        // Load emotion history from the file.
        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.loadFromFile(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
    }

    public void closeActivity() {
        this.finish();
    }

    // Parse the date input provided by the user.
    // Input: input - The date-string to parse.
    // Output: Returns a Date if parsing was successful or null otherwise.
    public Date parseDateInput(String input) {
        try {
            Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(input);
            return newDate;
        } catch (ParseException e) {
            return null;
        }
    }

    // Uses emotionController to delete an emotion and save changes.
    // Input: emotionId - The index of the emotion to delete.
    public void deleteEmotion(int emotionId) {
        EmotionController emotionController = FeelsBookApplication.getFeelsController();
        emotionController.deleteEmotion(emotionId);
        emotionController.saveChanges(this.getApplicationContext(), FeelsBookApplication.SAVE_FILE);
        closeActivity();
    }


    // Uses emotionController to update an emotion and save changes.
    // Input: emotionID - The index of the emotion to update.
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
