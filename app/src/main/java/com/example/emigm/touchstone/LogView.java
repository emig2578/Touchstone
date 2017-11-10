package com.example.emigm.touchstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;


public class LogView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);


        // Configure Scroll bar


        // Open file
        FileInputStream logfile;
        byte[] buffer = new byte[256];

        try {
            // Read from file
            logfile = openFileInput(getString(R.string.logfile_name));

           // while (logfile.read(buffer));
            System.out.println("Log successfully read :)");

            // Clean up text box


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to read log :(");
        }

        // Print text to page

    }
}
