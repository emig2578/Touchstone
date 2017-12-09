package com.example.emigm.touchstone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.io.FileOutputStream;
import android.widget.LinearLayout;
import java.util.Iterator;
import android.content.IntentFilter;

public class LogInput extends AppCompatActivity {

    // TEMPORARY: solution until I figure out how I want to build, maintain and consume the queue
    TS_Form current_form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_input);

        current_form = build_hardcode_anxiety_form();

        final Button log_submit_button = (Button)findViewById(R.id.submit_form);
        log_submit_button.setEnabled(false);

        final Button log_dismiss_button = (Button)findViewById(R.id.dismiss_form);

        LinearLayout form_content = (LinearLayout)findViewById(R.id.form_content);

        Iterator<TS_Widget> witerator = current_form.widgetIterator();

        while (witerator.hasNext()) {
            TS_Widget w = witerator.next();

            form_content.addView(w.getAndroidView());
        }

        IntentFilter filter = new IntentFilter("ACTION_FORM_READY");
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                boolean ready = intent.getBooleanExtra("ready", false);

                if (ready) {
                    log_submit_button.setEnabled(true);
                }

                else {
                    log_submit_button.setEnabled(false);
                }
            }
        }, filter);

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

        // Create widget text

        // Create recurrence info

        // Create form
        return new TS_Form("Anxiety", null, "", this.getApplicationContext());
    }

    private void form_ready_callback() {

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

   TODO: Home page
        - View log
        - Settings
        - ???

   TODO: Log viewer
        - Graph over time
        - Zoom in / out



*/