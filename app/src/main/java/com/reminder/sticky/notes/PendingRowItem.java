package com.reminder.sticky.notes;

/**
 * Created by noman on 16/02/15.
 */
public class PendingRowItem {
    private String reminderid;
    private String remindertext;
    private String reminderdatetime;
    private String remindertype;

    public PendingRowItem(String reminderid,String remindertype,String remindertext,String reminderdatetime) {
        this.reminderid=reminderid;
        this.remindertype=remindertype;
        this.remindertext=remindertext;
        this.reminderdatetime=reminderdatetime;
    }
    public String getreminderid() {
        return reminderid;
    }
    public void setreminderid(String reminderid) {
        this.reminderid=reminderid;
    }
    public String getremindertext() {
        return remindertext;
    }
    public void setremindertext(String remindertext) {
        this.remindertext = remindertext;
    }

    public String getremindertype() {
        return remindertype;
    }
    public void setremindertype(String remindertype) {
        this.remindertype = remindertype;
    }
    public String getreminderdatetime() {
        return reminderdatetime;
    }
    public void setreminderdatetime(String reminderdatetime) {
        this.reminderdatetime = reminderdatetime;
    }

    @Override
    public String toString() {
        return remindertext;
    }


}
