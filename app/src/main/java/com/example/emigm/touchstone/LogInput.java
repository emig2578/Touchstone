package com.example.emigm.touchstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LogInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_input);
    }
}


/*

// TODO: Sliding scale
        - Add 0 / 50 / 100 scale
        - Maybe need to manually do layout

   TODO: Event dialog under log input
        - Pop up?
        - Text cap characters
        - Color
        - Date / time
        - Submit
            - Package into XML + write to file

   TODO: Button handler
        - Convert sliding scale position to 0.0 - 100.0
        - Package the following items into XML:
            - Current date + time
            - Sliding scale position
            - Words
        - Write to file

   TODO: Settings page
        - Notification time / repetition
        - Notification LED color
        - Notification sound

   TODO: Home page
        - View log
        - Settings
        - Add a known event
        - ???

   TODO: Log viewer
        - Graph over time
        - Zoom in / out
        - Event display
*/