package com.example.emigm.touchstone;

import android.view.View;
import android.widget.EditText;
import android.content.Context;

public class TS_Text_Widget implements TS_Widget {

    String label = "";
    int index = 0;

    EditText data;

    public TS_Text_Widget(String label, Context context) {
        // create data field
        data = new EditText(context);
    }

    // Packages widget data into TS xml scheme
    public String toEntryData() {
        return data.getText().toString();
    }

    // Returns label of this widget (blank by default)
    public String getLabel() {
        return label;
    }

    // Returns whether updating this widget is required in order to submit form (not required by default)
    public boolean isRequired() {
        return true;
    }

    // Reset widget content to its default state
    public void clearData() {
        data.clearComposingText();
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    // Returns the Android View object associated with this widget
    public View getAndroidView() {
        return data;
    }

}
