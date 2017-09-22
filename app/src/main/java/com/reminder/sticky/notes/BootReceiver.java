package com.reminder.sticky.notes;

/**
 * Created by noman on 16/02/15.
 */
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // TODO Auto-generated method stub
        //if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction()) || "android.intent.action.QUICKBOOT_POWERON".equals(intent.getAction())) {

            Alarm_Db db=new Alarm_Db(context.getApplicationContext());

            try{
                db.open();
                db.getData();
                db.close();
            }catch(Exception e){}
            String reminderid[]={},remindertype[]={},remindertext[]={},reminderdate[]={},remindertime[]={},reminderdatetime[]={};
            try{
                reminderid=db.getReminderId();
                remindertype=db.getReminderType();
                remindertext=db.getReminderText();
                reminderdate=db.getReminderDate();
                remindertime=db.getReminderTime();
            }catch(Exception e){}
            int remindId;
            Calendar now;

            for(int i=0;i<reminderid.length;i++){
                String retval[]= reminderdate[i].split("/");
                int day=Integer.parseInt(retval[0]);
                int month=Integer.parseInt(retval[1]);
                int year=Integer.parseInt(retval[2]);
                remindId=Integer.parseInt(reminderid[i]);
                String retval1[]=remindertime[i].split(":");
                int hours=Integer.parseInt(retval1[0]);
                int minutes=Integer.parseInt(retval1[1]);
                try{
                    db.open();
                    db.deletereminder(remindId);
                    db.close();
                }catch(Exception e){
                }
                now=Calendar.getInstance();
                month=month-1;
                now.set(year, month,day,hours,minutes);
                now.set(Calendar.SECOND,0);
                SharedPreferences alarmprefs=context.getApplicationContext().getSharedPreferences("alarmid", context.getApplicationContext().MODE_PRIVATE);
                int alarmid=alarmprefs.getInt("alarmid", 1);
                String text=remindertext[i];
                String datestring=reminderdate[i];
                String timestring=remindertime[i];
                String typestring=remindertype[i];
                AlarmManager alarms = (AlarmManager) context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent ringservice=new Intent("android.intent.Alarm_setter");
                ringservice.putExtra("remindertext", text);
                ringservice.putExtra("alarmid", alarmid);
                PendingIntent pendingringservice=PendingIntent.getService(context.getApplicationContext(), alarmid, ringservice, PendingIntent.FLAG_UPDATE_CURRENT);
                int type=Integer.parseInt(typestring);
                switch(type)
                {
                    case 0:
                        //Toast.makeText(getActivity(), "Never", Toast.LENGTH_SHORT).show();
                        alarms.set(AlarmManager.RTC_WAKEUP,now.getTimeInMillis(), pendingringservice);
                        break;
                    case 1:
                        //Toast.makeText(getActivity(), "Fifteen", Toast.LENGTH_SHORT).show();
                        alarms.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingringservice);
                        //alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),1000, pendingringservice);
                        break;
                    case 2:
                        alarms.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR, pendingringservice);

                        break;
                    case 3:
                        alarms.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingringservice);

                        break;
                    case 4:
                        alarms.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingringservice);

                        break;
                    case 5:
                        alarms.setRepeating(AlarmManager.RTC_WAKEUP, now.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingringservice);

                        break;
                }
                try {
                    db.open();
                    db.createentry(alarmid,text, datestring, timestring,typestring);
                    db.close();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    //Toast.makeText(getActivity(), "Database Failed", Toast.LENGTH_SHORT).show();
                }
                SharedPreferences.Editor edit=alarmprefs.edit();
                alarmid++;
                edit.putInt("alarmid", alarmid);
                edit.commit();
                //Toast.makeText(getActivity(), "Reminder Set", Toast.LENGTH_SHORT).show();
            }

            // List<PendingRowItem> rowItems;
            //rowItems = new ArrayList<PendingRowItem>();
            //CustomReminderListAdapter adapter = new CustomReminderListAdapter(context,
            //         R.layout.pendinglist, rowItems);
            //adapter.notifyDataSetChanged();




       // }


    }

}

