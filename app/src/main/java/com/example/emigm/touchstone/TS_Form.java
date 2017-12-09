package com.example.emigm.touchstone;
import android.app.AlarmManager;
import java.util.Iterator;
import android.content.Context;

public class TS_Form {

    public int MAX_WIDGETS_PER_FORM = 32;

    private String Name;
    private TS_Recurrence_Info Recurrence;
    private TS_Widget[] Entry_Fields;

    private int Widgets_Ready = 0;

    public enum TS_Recurrence_Increment {

        MINUTE,
        HOUR,
        DAY,
        WEEK,
        MONTH
    }

    // TODO: is String the way we do this?  Does DOM have a better way of passing this data around?
    public TS_Form(String name, TS_Recurrence_Info recurrence, String entry_fields_xml, Context context) {

        setName(name);
        setRecurrence(recurrence);

        // Process xml - fail if no widgets / widgets not built properly


        // TEMPORARY: hard code anxiety form list
        Entry_Fields = new TS_Widget[]{new TS_Text_Widget("Words", context)};


        // Update widgets ready mask
        for(int i = 0; i < Entry_Fields.length; i++) {

            if (Entry_Fields[i].isRequired()) {
                notifyReady(i, false);
            }
        }

    }

    // Get the friendly name of this form
    public String getName() {
        return this.Name;
    }

    // Update the friendly name of this form
    public void setName(String name) {
        this.Name = name;
    }

    // Set the notification recurrence for this form
    public boolean setRecurrence(TS_Recurrence_Info recurrence) {
        this.Recurrence = recurrence;

        // If this has been set before, cancel the other alarm

        // Set alarm

        return false;
    }


    // Get the current recurrence info of this form
    public TS_Recurrence_Info getRecurrence() {
        return this.Recurrence;
    }


    // Convert the contents of this form to xml
    public String toEntry() {

        return "";
    }

    // Reset all data in this form
    public void clearEntryData() {

        for (int i = 0; i < Entry_Fields.length; i++) {
            Entry_Fields[i].clearData();
        }

    }

    // Used by widgets to notify when they are ready to be submitted.  Returns true if all widgets in form are ready
    public boolean notifyReady(int id, boolean ready) {

        boolean was_ready = (Widgets_Ready == 0);

        int mask = ~((ready ? 1 : 0) << id);

        // we want to clear this bit
        if (ready) Widgets_Ready &= mask;

        // we want to set this bit
        else Widgets_Ready |= mask;

        // all widgets are ready
        if (Widgets_Ready == 0) {

            // Broadcast widgets ready event - LogInput listens and updates accordingly
            // note: in android terminology we want to "broadcast" an "intent"
            return true;
        }
        else if (was_ready) {

            // Broadcast widgets not ready event - LogInput listens and updates accordingly
        }
        return false;
    }

    public void delete() {

    }

    public Iterator<TS_Widget> widgetIterator() {
        return new Iterator<TS_Widget>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                if (index >= Entry_Fields.length)
                    return false;

                return true;
            }

            @Override
            public TS_Widget next() {
                return Entry_Fields[index++];
            }
        };
    }

    public class TS_Recurrence_Info {
        public TS_Recurrence_Increment Increment;
        public int Period;

        public TS_Recurrence_Info(TS_Recurrence_Increment increment, int period) {
            Increment = increment;
            Period = period;
        }
    }

}
