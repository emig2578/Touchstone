package com.example.emigm.touchstone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.FileOutputStream;
import android.widget.LinearLayout;
import java.util.Iterator;

public class LogInput extends AppCompatActivity {

    // TEMPORARY: solution until I figure out how I want to build, maintain and consume the queue
    TS_Form current_form = build_hardcode_anxiety_form();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_input);

        final Button log_submit_button = (Button)findViewById(R.id.submit_form);
        log_submit_button.setEnabled(false);

        final Button log_dismiss_button = (Button)findViewById(R.id.dismiss_form);

        LinearLayout form_content = (LinearLayout)findViewById(R.id.form_content);

        Iterator<TS_Widget> witerator = current_form.widgetIterator();

        while (witerator.hasNext()) {
            TS_Widget w = witerator.next();

            form_content.addView(w.getAndroidView());
        }

        // TODO: inflate everything

        // TODO: set up event listener for form ready / not ready events

        log_submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get timestamp
                String log = "";
                long timestamp = System.currentTimeMillis();

                String filename = getString(R.string.logfile_name);
                FileOutputStream outputStream;

                try {
                    // Write content to file
                    outputStream = openFileOutput(filename, Context.MODE_APPEND);

                    outputStream.write(current_form.toEntry().getBytes());

                    System.out.println("Log successfully written :)");

                    // Clean up form
                    current_form.clearEntryData();

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to write log :(");
                }
            }
        });

        log_dismiss_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO: pop up - are you sure you want to dismiss this form?
            }
        });
    }

    // TEMPORARY: for messing around with form structure before custom forms enabled
    private TS_Form build_hardcode_anxiety_form() {

        // Create widgets

        // Create recurrence info

        // Create form
        return new TS_Form("Anxiety", null, "");

    }
}


/*

   TODO: Sliding scale UI
        - Add 0 / 50 / 100 scale
        - Make less ugly

   TODO: Text input box UI
        - Make a box
            - Inside of box 10% saturation
            - Outside of box 80% saturation
        - Make text wrap around in box

   TODO: Notifications
        - Schedule to trigger once per day
        - Launch LogInput activity

   TODO: Button UI
        - Match rest of UI

   TODO: Button handler
        - Convert sliding scale position to 0.0 - 100.0

   TODO: Settings page
        - Notification time / repetition
        - Theme color
        - Notification sound

   TODO: Events
        - Pop up?
        - Text cap characters
        - Color
        - Date / time
        - Submit
            - Package into XML + write to file

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