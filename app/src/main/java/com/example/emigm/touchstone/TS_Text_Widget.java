package com.example.emigm.touchstone;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import com.example.emigm.touchstone.TS_Form;

public class TS_Text_Widget implements TS_Widget {

    private String label = "";
    private int index = 0;

    private EditText data;

    private boolean hasData = false;

    private Context m_context;

    public TS_Text_Widget(String label, Context context) {

        this.label = label;

        m_context = context;

        // create data field
        data = new EditText(context);

        // TODO: format the EditText (right now it's like 2 characters wide)

        data.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int before, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

                if (!hasData && (s.length() > 0)) {
                    sendReadyNotification(true);
                }
                else if (hasData && s.length() < 1){
                    sendReadyNotification(false);
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

    private void sendReadyNotification(boolean ready) {

        System.out.println("sending ready notification: "+ready+" from widget "+this.index);

        hasData = ready;

        // Broadcast WIDGET_READY broadcast w/ ready as data
        Intent i = new Intent(ACTION_WIDGET_READY);
        i.putExtra("id", this.index);
        i.putExtra("ready", ready);

        System.out.println("sending broadcast");
        m_context.sendBroadcast(i);
    }

}
