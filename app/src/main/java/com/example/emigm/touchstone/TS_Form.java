package com.example.emigm.touchstone;
import android.app.AlarmManager;
import java.util.Iterator;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import com.example.emigm.touchstone.TS_Widget;

public class TS_Form {

    public static final String ACTION_FORM_READY = "com.example.emigm.touchstone.ACTION_FORM_READY";

    public int MAX_WIDGETS_PER_FORM = 32;

    private String Name;
    private TS_Recurrence_Info Recurrence;
    private TS_Widget[] Entry_Fields;
    private Context m_context;

    private boolean[] Widgets_Ready;

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
        m_context = context;

        // TEMPORARY: hard code anxiety form list
        Entry_Fields = new TS_Widget[]{new TS_Slider_Widget("Level of functioning", context), new TS_Text_Widget("Words", 500, context)};

        Widgets_Ready = new boolean[Entry_Fields.length];

        // Update indexes and widgets ready mask
        for(int i = 0; i < Entry_Fields.length; i++) {

            Entry_Fields[i].setIndex(i);

            if (Entry_Fields[i].isRequired()) {
                Widgets_Ready[i] = false;
            }
            else {
                Widgets_Ready[i] = true;
            }
        }

        IntentFilter filter = new IntentFilter(TS_Widget.ACTION_WIDGET_READY);
        m_context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                int id = intent.getIntExtra("id", -1);
                boolean ready = intent.getBooleanExtra("ready", false);

                if (id >= 0 && id < Entry_Fields.length) {
                    notifyReady(id, ready);
                }
                else {
                    System.out.println("invalid id");
                }
            }
        }, filter);
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

    // Callback for broadcast from widgets to notify when they are ready to be submitted.  Returns true if all widgets in form are ready
    public void notifyReady(int id, boolean ready) {
        boolean form_was_ready = form_ready();

        Widgets_Ready[id] = ready;

        if (form_ready()) {
            sendFormReadyNotification(true);
        }

        else if (form_was_ready) {
            sendFormReadyNotification(false);
        }
    }

    private boolean form_ready() {
        for (int i = 0; i < Widgets_Ready.length; i++) {
            if (!Widgets_Ready[i]) {
                return false;
            }
            else {
            }
        }
        return true;
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

    private void sendFormReadyNotification(boolean ready) {

        // Broadcast WIDGET_READY broadcast w/ ready as data
        Intent i = new Intent(ACTION_FORM_READY);
        i.putExtra("ready", ready);

        m_context.sendBroadcast(i);

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
