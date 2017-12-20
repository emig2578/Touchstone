package com.example.emigm.touchstone;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.content.Context;
import android.content.Intent;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.content.BroadcastReceiver;
import com.example.emigm.touchstone.TS_Form;

public class TS_Text_Widget implements TS_Widget {

    private String label = "";
    private int index = 0;

    private EditText data;
    private ScrollView scroll;
    private FrameLayout frame;

    private boolean hasData = false;

    private Context m_context;

    public TS_Text_Widget(String label, int height, Context context) {

        this.label = label;

        m_context = context;

        // create frame
        frame = new FrameLayout(context);
        frame.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
        frame.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));

        // create scrolling container
        scroll = new ScrollView(context);
        ScrollView.LayoutParams s = new ScrollView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        s.setMargins(2, 2, 2, 2);
        scroll.setLayoutParams(s);
        scroll.setBackgroundColor(context.getResources().getColor(R.color.colorPrimaryLight));
        frame.addView(scroll);


        // create data field
        data = new EditText(context);
        ViewGroup.LayoutParams l = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        data.setLayoutParams(l);
        scroll.addView(data);

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
        // TEMPORARY: hard code until DOM stuff

        String out = "<text \n";
        out += "\tlabel=\""+label+"\">\n";
        out += "\t"+data.getText().toString()+"\n";
        out += "</text>";

        return out;
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
        System.out.println("Clear data");
        data.setText("");
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    // Returns the Android View object associated with this widget
    public View getAndroidView() {
        return frame;
    }

    private void sendReadyNotification(boolean ready) {

        hasData = ready;

        System.out.println("I am widget "+this.index+" and I am "+(ready?"":"not ")+"ready");

        // Broadcast WIDGET_READY broadcast w/ ready as data
        Intent i = new Intent(ACTION_WIDGET_READY);
        i.putExtra("id", this.index);
        i.putExtra("ready", ready);

        m_context.sendBroadcast(i);
    }

}
