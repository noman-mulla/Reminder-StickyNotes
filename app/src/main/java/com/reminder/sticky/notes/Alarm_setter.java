package com.reminder.sticky.notes;

/**
 * Created by noman on 16/02/15.
 */
import java.util.Calendar;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

public class Alarm_setter extends Service{

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub

        //SharedPreferences alarmprefs=getApplicationContext().getSharedPreferences("alarmid", getApplicationContext().MODE_PRIVATE);
        //int alarmid=alarmprefs.getInt("alarmid", 1);
        int alarmid=intent.getIntExtra("alarmid", 1);
        String remindertext=intent.getStringExtra("remindertext");
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.drawable.icon_reminder);
        mBuilder.setContentTitle("Reminder");
        mBuilder.setContentText(remindertext);

       mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);

        //mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        //mBuilder.setDefaults(Notification.FLAG_SHOW_LIGHTS);

        mBuilder.setAutoCancel(true);



        Intent result=new Intent(this,MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(result);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(alarmid, mBuilder.build());
        //Toast.makeText(getApplicationContext(), remindertext, Toast.LENGTH_LONG).show();
        Alarm_Db db=new Alarm_Db(getApplicationContext());
        String type="";
        try{
            db.open();
            db.getData();
            type=db.gettype(alarmid);
            db.close();
        }catch(Exception e){}
        if(type.equals("0")){
            //Toast.makeText(getApplicationContext(), "Never", Toast.LENGTH_LONG).show();
            try{
                db.open();
                db.deletereminder(alarmid);
                db.close();
            }catch(Exception e){}
        }
        if(type.equals("1")){
            Calendar now=Calendar.getInstance();
            now.add(Calendar.MINUTE, 15);
            //Date time=now.getTime();
            //String time1=time.toString();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            int minutes=now.get(Calendar.MINUTE);
            // int seconds=now.get(Calendar.SECOND);
            int day=now.get(Calendar.DAY_OF_MONTH);
            int month=now.get(Calendar.MONTH);
            int year=now.get(Calendar.YEAR);
            int forStringMonth=month+1;
            String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
            String timestring=Integer.toString(hour)+":"+Integer.toString(minutes);
            //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
            try{
                db.open();
                db.updateentry(alarmid, datestring, timestring);
                db.close();
            }catch(Exception e){}
        }
        if(type.equals("2")){
            Calendar now=Calendar.getInstance();
            now.add(Calendar.MINUTE, 30);
            //Date time=now.getTime();
            //String time1=time.toString();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            int minutes=now.get(Calendar.MINUTE);
            // int seconds=now.get(Calendar.SECOND);
            int day=now.get(Calendar.DAY_OF_MONTH);
            int month=now.get(Calendar.MONTH);
            int year=now.get(Calendar.YEAR);
            int forStringMonth=month+1;
            String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
            String timestring=Integer.toString(hour)+":"+Integer.toString(minutes);
            //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
            try{
                db.open();
                db.updateentry(alarmid, datestring, timestring);
                db.close();
            }catch(Exception e){}
        }
        if(type.equals("3")){
            Calendar now=Calendar.getInstance();
            //now.add(Calendar.MINUTE, 60);
            now.add(Calendar.HOUR_OF_DAY, 1);
            //Date time=now.getTime();
            //String time1=time.toString();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            int minutes=now.get(Calendar.MINUTE);
            // int seconds=now.get(Calendar.SECOND);
            int day=now.get(Calendar.DAY_OF_MONTH);
            int month=now.get(Calendar.MONTH);
            int year=now.get(Calendar.YEAR);
            int forStringMonth=month+1;
            String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
            String timestring=Integer.toString(hour)+":"+Integer.toString(minutes);
            //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
            try{
                db.open();
                db.updateentry(alarmid, datestring, timestring);
                db.close();
            }catch(Exception e){}
        }
        if(type.equals("4")){
            Calendar now=Calendar.getInstance();
            //now.add(Calendar.MINUTE, 15);
            now.add(Calendar.DAY_OF_MONTH, 1);
            //Date time=now.getTime();
            //String time1=time.toString();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            int minutes=now.get(Calendar.MINUTE);
            // int seconds=now.get(Calendar.SECOND);
            int day=now.get(Calendar.DAY_OF_MONTH);
            int month=now.get(Calendar.MONTH);
            int year=now.get(Calendar.YEAR);
            int forStringMonth=month+1;
            String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
            String timestring=Integer.toString(hour)+":"+Integer.toString(minutes);
            //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
            try{
                db.open();
                db.updateentry(alarmid, datestring, timestring);
                db.close();
            }catch(Exception e){}
        }
        if(type.equals("5")){
            Calendar now=Calendar.getInstance();
            //now.add(Calendar.MINUTE, 15);
            now.add(Calendar.DAY_OF_MONTH, 7);
            //Date time=now.getTime();
            //String time1=time.toString();
            int hour=now.get(Calendar.HOUR_OF_DAY);
            int minutes=now.get(Calendar.MINUTE);
            // int seconds=now.get(Calendar.SECOND);
            int day=now.get(Calendar.DAY_OF_MONTH);
            int month=now.get(Calendar.MONTH);
            int year=now.get(Calendar.YEAR);
            int forStringMonth=month+1;
            String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
            String timestring=Integer.toString(hour)+":"+Integer.toString(minutes);
            //Toast.makeText(getActivity().getApplicationContext(), Integer.toString(minutes), Toast.LENGTH_SHORT).show();
            try{
                db.open();
                db.updateentry(alarmid, datestring, timestring);
                db.close();
            }catch(Exception e){}
        }
        stopSelf();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

}

