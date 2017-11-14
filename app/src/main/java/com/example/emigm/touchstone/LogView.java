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
        String test_text;

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

        test_text = logViewText.getText().toString();

        format_entries(test_text);
    }

    private void format_entries(String raw_xml) {
        long timestamp;
        int level;
        String text;
        String entry_buffer[];
        String attributes_buffer[];

        // grab each entry
        entry_buffer = raw_xml.split("</entry>");

        for (int i = 0; i < entry_buffer.length; i++) {

            // split between entry attributes and text.
            attributes_buffer = entry_buffer[i].split(">\n\t");


            if (attributes_buffer.length < 2)
            {
                System.out.println("bad format");
                return;
            }

            System.out.println("attributes: " + attributes_buffer[0]);
            System.out.println("words: " + attributes_buffer[1]);

            // System.out.println(attributes_buffer[1]);
            //timestamp = attributes_buffer.

           // System.out.println(entry_buffer[i]);
        }
    }

    private void draw_entry(long timestamp, int level, String text) {

    }
}
