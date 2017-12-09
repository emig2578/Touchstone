package com.example.emigm.touchstone;

import android.text.Editable;
import android.text.TextWatcher;
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
