package com.reminder.sticky.notes;

/**
 * Created by noman on 16/02/15.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class Alarm_Db {

    public static final String KEY_ROWID="_id";
    public static final String KEY_REMINDER="Reminder_text";
    public static final String KEY_DATE="Reminder_date";
    public static final String KEY_TIME="Reminder_time";
    public static final String KEY_REMINDERTYPE="Reminder_type";



    private static final String DATABASE_NAME="Alarmdb";
    private static final String DATABASE_TABLE="alarmtable";

    private static final int DATABASE_VERSION=1;

    private DbHelper ourhelper;
    private final  Context ourcontext;
    private SQLiteDatabase ourdatabase;

    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub

            db.execSQL("CREATE TABLE "+DATABASE_TABLE+" ("+
                            KEY_ROWID+" INTEGER PRIMARY KEY , "+
                            KEY_REMINDERTYPE+" TEXT NOT NULL , "+
                            KEY_REMINDER+" TEXT NOT NULL, "+
                            KEY_DATE+" TEXT NOT NULL, "+
                            KEY_TIME+" TEXT NOT NULL);"
            );



        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);

            onCreate(db);

        }


    }

    public Alarm_Db(Context c){
        ourcontext=c;

    }
    public Alarm_Db open()throws Exception{
        ourhelper =new DbHelper(ourcontext);
        ourdatabase =ourhelper.getWritableDatabase();
        return this;

    }
    public void close(){
        ourhelper.close();
    }

    public long createentry(int id,String remindertext,String date,String time,String type) {
        // TODO Auto-generated method stub
        ContentValues cv=new ContentValues();
        cv.put(KEY_ROWID, id);
        cv.put(KEY_REMINDERTYPE, type);
        cv.put(KEY_REMINDER, remindertext);
        cv.put(KEY_DATE, date);
        cv.put(KEY_TIME, time);

        return ourdatabase.insert(DATABASE_TABLE, null, cv);

    }

    String reminderid[],remindertype[],remindertext[],reminderdatetime[],reminderdate[],remindertime[];
    public void getData() {
        // TODO Auto-generated method stub
        String[] columns=new String[]{KEY_ROWID,KEY_REMINDERTYPE,KEY_REMINDER,KEY_DATE,KEY_TIME};
        Cursor c= ourdatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        //Cursor crs=ourdatabase.rawQuery("Select "+KEY_ROWID+" from "+DATABASE_TABLE, null);
        //String[] result=new String[c.getCount()];
        reminderid=new String[c.getCount()];
        remindertype=new String[c.getCount()];
        remindertext=new String[c.getCount()];
        reminderdatetime=new String[c.getCount()];
        reminderdate=new String[c.getCount()];
        remindertime=new String[c.getCount()];
        int iRow=c.getColumnIndex(KEY_ROWID);
        int iType=c.getColumnIndex(KEY_REMINDERTYPE);
        int iReminder=c.getColumnIndex(KEY_REMINDER);
        int iDate=c.getColumnIndex(KEY_DATE);
        int iTime=c.getColumnIndex(KEY_TIME);
        int i=0;
        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            //result[i]=c.getString(iRow)+" "+c.getString(iType)+" "+c.getString(iReminder)+ " "+c.getString(iDate)+" "+c.getString(iTime);
            reminderid[i]=c.getString(iRow);
            remindertype[i]=c.getString(iType);
            remindertext[i]=c.getString(iReminder);
            reminderdate[i]=c.getString(iDate);
            remindertime[i]=c.getString(iTime);
            reminderdatetime[i]="Date:"+c.getString(iDate)+"\n"+"Time:"+c.getString(iTime);
            i++;
        }

        //return result;
    }

    public String[] getReminderId(){
        return reminderid;
    }
    public String[] getReminderType(){

        return remindertype;

    }
    public String gettype(int id){

        String[] columns=new String[]{KEY_ROWID,KEY_REMINDERTYPE,KEY_REMINDER,KEY_DATE,KEY_TIME};
        Cursor c= ourdatabase.query(DATABASE_TABLE, columns, KEY_ROWID +"=" +id, null, null, null, null);
        c.moveToFirst();
        String type=c.getString(1);
        return type;
    }

    public String[] getReminderText(){

        return remindertext;

    }
    public String[] getReminderDateTime(){
        return reminderdatetime;
    }
    public String[] getReminderDate(){
        return reminderdate;
    }
    public String[] getReminderTime(){
        return remindertime;
    }

    public void deletereminder(int id){
        ourdatabase.delete(DATABASE_TABLE, KEY_ROWID+"="+id, null);
    }

    public void updateentry(int id, String datestring, String timestring) {
        // TODO Auto-generated method stub
        ContentValues cvup=new ContentValues();
        cvup.put(KEY_DATE, datestring);
        cvup.put(KEY_TIME, timestring);
        ourdatabase.update(DATABASE_TABLE, cvup, KEY_ROWID +"="+ id, null);


    }
}
