package com.example.emigm.touchstone;
import android.app.AlarmManager;


public class TS_Form {

    private String Name;
    private TS_Recurrence_Info Recurrence;
    private TS_Widget[] Entry_Fields;

    public enum TS_Recurrence_Increment {

        MINUTE,
        HOUR,
        DAY,
        WEEK,
        MONTH
    }

    // TODO: is String the way we do this?  Does DOM have a better way of passing this data around?
    public TS_Form(String name, TS_Recurrence_Info recurrence, String entry_fields_xml) {

    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    // TODO: need to figure out how to re-set an alarm.  Ability to check and manipulate alarms in the system would do it.
    // TODO: can an app have multiple AlarmManagers setting alarms?

    public boolean setRecurrence(int type, long triggerAtMillis, long intervalMillis) {
        // TODO: build intent w/ LogInput and pass this form as data
        //AlarmManager.setRepeating(type, triggerAtMillis, intervalMillis, new Intent()
        return false;
    }

    public TS_Recurrence_Info getRecurrence() {
        return this.Recurrence;
    }

    public String toEntry() {
        return "";
    }

    public void clearEntryData() {
        for (int i = 0; i < Entry_Fields.length; i++) {
            Entry_Fields[i].clearData();
        }
    }

    public void drawForm() {

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
