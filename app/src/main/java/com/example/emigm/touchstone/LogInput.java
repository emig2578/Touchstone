package com.example.emigm.touchstone;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import java.io.FileOutputStream;
import android.content.Intent;
import org.xml.sax.*;

public class LogInput extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_input);

        final Button log_submit_button = (Button)findViewById(R.id.log_submit_button);
        log_submit_button.setEnabled(false);

        final Button stupid_button = (Button)findViewById(R.id.stupid_button);

        stupid_button.setOnClickListener(new View.OnClickListener(){
           public void onClick(View v) {
               Intent stupid_intent = new Intent(LogInput.this, LogView.class);
               startActivity(stupid_intent);
           }
        });

        final EditText log_text = (EditText)findViewById(R.id.logEntryText);
        log_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                // TODO: is there a better way to keep track of this (so we don't call length() every time the text changes)?
                if (s.length() > 0) {

                    // TODO: figure out how to do the color change automatically - could override this function, but there's probably a button parameter
                    log_submit_button.setEnabled(true);
                    log_submit_button.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
                else {
                    log_submit_button.setEnabled(false);
                    log_submit_button.setTextColor(getResources().getColor(R.color.colorPrimary));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });

        log_submit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                // get timestamp
                String log = "";
                long timestamp = System.currentTimeMillis();

                // get level
                // TODO: plumb this in once sliding scale is ready
                int level = ((SeekBar)findViewById(R.id.slidingScaleObject)).getProgress();


                // TODO: use DOM
                // TODO: add object tag to message
                // Write to file
                log += "<entry\n";
                log += "\t time="+timestamp+"\n";
                log += "\t level="+level+">\n\t";
                log += (((EditText)findViewById(R.id.logEntryText)).getText());
                log += "\n</entry>\n\n\n";

                String filename = getString(R.string.logfile_name);
                FileOutputStream outputStream;

                try {
                    // Write content to file
                    outputStream = openFileOutput(filename, Context.MODE_APPEND);
                    outputStream.write(log.getBytes());
                    System.out.println("Log successfully written :)");

                    // Clean up text box
                    log_text.setEnabled(false);
                    log_text.setText("");
                    log_text.setEnabled(true);

                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Failed to write log :(");
                }
            }
        });
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