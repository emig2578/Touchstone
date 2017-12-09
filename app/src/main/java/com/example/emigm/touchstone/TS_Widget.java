package com.example.emigm.touchstone;

import android.view.View;

public interface TS_Widget {

    public static final String ACTION_WIDGET_READY = "com.example.emigm.touchstone.WIDGET_READY";

    String label = "";
    boolean required = false;
    int index = 0;

    // Packages widget data into TS xml scheme
    String toEntryData();

    // Returns label of this widget (blank by default)
    String getLabel();

    // Returns whether updating this widget is required in order to submit form (not required by default)
    boolean isRequired();

    // Reset widget content to its default state
    void clearData();

    // Returns the Android View object associated with this widget
    View getAndroidView();

    void setIndex(int index);

    int getIndex();
}
