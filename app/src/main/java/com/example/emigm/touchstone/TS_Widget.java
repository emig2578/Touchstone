package com.example.emigm.touchstone;

public interface TS_Widget {

    String label = "";

    String toEntryData();
    String getLabel();
    String setLabel(String label);
    void clearData();

}
