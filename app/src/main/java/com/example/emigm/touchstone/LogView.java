package com.example.emigm.touchstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;

import android.widget.TextView;

public class LogView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        // TODO: prettier formatting

        // Open file
        FileInputStream logfile;
        byte[] buffer = new byte[256];
        TextView logViewText = (TextView)findViewById(R.id.logViewText);

        try {
            // Read from file
            logfile = openFileInput(getString(R.string.logfile_name));

            while (logfile.read(buffer) > 0) {
                // TODO: make sure this is the right charset to use
                logViewText.append(new String(buffer, "US-ASCII"));

                // TODO: Coming from C, this seems very very wrong.  Given garbage collection is this really the best way?  Does java have memset?
                buffer = new byte[256];
            }

           System.out.println("Log successfully read :)");

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Failed to read log :(");

        }

    }
}
