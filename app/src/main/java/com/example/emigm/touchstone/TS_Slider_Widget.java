package com.example.emigm.touchstone;

import android.view.View;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;

public class TS_Slider_Widget implements TS_Widget {

    private String label = "";
    private int index = 0;

    private SeekBar data;
    private FrameLayout frame;

    private boolean hasData = false;

    private Context m_context;

    public TS_Slider_Widget(String label, Context context) {
        this.label = label;

        this.data = new SeekBar(context);
        this.frame = new FrameLayout(context);

        FrameLayout.LayoutParams f = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        f.setMargins(0, 10, 0, 50);

        data.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        frame.addView(data);
    }

    // Packages widget data into TS xml scheme
    public String toEntryData() {
        // TEMPORARY: hard code until DOM stuff is added
        String out = "<slider \n";
        out += "\tlabel=\""+label+"\"\n";
        out += "\tvalue=\""+this.data.getProgress()+"\"\n";
        out += "\t\t/>\n";
        return out;
    }

    // Returns label of this widget (blank by default)
    public String getLabel() {
        return label;
    }

    // Returns whether updating this widget is required in order to submit form (not required by default)
    public boolean isRequired() {
        return false;
    }

    // Reset widget content to its default state
    public void clearData() {
        // keep level of the sliding scale in case user wants to input multiple entries
    }

    // Returns the Android View object associated with this widget
    public View getAndroidView() {
        return frame;
    }

    public void setIndex(int index) {
        this.index = index;
    }


    public int getIndex() {
        return index;
    }
}
