package com.example.emigm.touchstone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.io.FileInputStream;
import android.widget.TextView;
import java.util.Date;
import android.util.AttributeSet;

public class LogView extends AppCompatActivity {

    private int x_offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_view);

        // TODO: prettier formatting

        // Open file
        FileInputStream logfile;
        byte[] buffer = new byte[256];
        String test_text = "";

        try {
            // Read from file
            logfile = openFileInput(getString(R.string.logfile_name));

            while (logfile.read(buffer) > 0) {
                // TODO: make sure this is the right charset to use
                test_text += (new String(buffer, "US-ASCII"));

                // TODO: Coming from C, this seems very very wrong.  Given garbage collection is this really the best way?  Does java have memset?
                buffer = new byte[256];
            }

           System.out.println("Log successfully read :)");

        } catch (Exception e) {

            e.printStackTrace();
            System.out.println("Failed to read log :(");
        }

        format_entries(test_text);
    }

    private void format_entries(String raw_xml) {
        String timestamp;
        String level;
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

            timestamp = (attributes_buffer[0].split("time=")[1]);
            timestamp = timestamp.split("\n")[0];

            level = (attributes_buffer[0].split("level=")[1]);
            level = level.split(">")[0];

            draw_entry(Long.valueOf(timestamp), Integer.valueOf(level), attributes_buffer[1], x_offset);
        }
    }


    // Draws a log entry on the view page, returns height of text box
    private int draw_entry(long timestamp, int level, String text, int offset) {

        TextView t = new TextView(LogView.this);

        System.out.println("timestamp = "+timestamp);
        System.out.println("level = "+level);
        System.out.println("words = "+text);

        Date date = new Date(timestamp);

        System.out.println("Date = "+date.toString());
        System.out.println("\n");

        t.setText("\n\n"+date.toString()+"\n\n"+text+"\n");

        t.setBackgroundColor(level*(getResources().getColor(R.color.colorAccent))/100);
        t.setTextColor(getResources().getColor(R.color.colorAccentDark));

        //TODO: replace this with colored text boxes
        TextView logViewText = (TextView)findViewById(R.id.logViewText);
        logViewText.append(t.getText());

        // TODO: figure out how to draw within scrollview
            // - Draw at x value "offset"

        return t.getHeight();
    }


}
