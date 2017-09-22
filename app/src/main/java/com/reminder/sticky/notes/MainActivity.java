package com.reminder.sticky.notes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class MainActivity extends ActionBarActivity implements OnItemClickListener {

    public static final String[] titles = new String[] { "Add Reminder",
            "Pending Reminders" ,"Rate Us"};

    public static final Integer[] images = { R.drawable.device_access_add_alarm,
            R.drawable.navigation_accept,R.drawable.ic_action_good};

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    List<RowItem> rowItems;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rowItems = new ArrayList<RowItem>();
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(images[i], titles[i]);
            rowItems.add(item);
        }
        mTitle = mDrawerTitle = getTitle();
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        CustomListViewAdapter adapter = new CustomListViewAdapter(this,
                R.layout.customlist, rowItems);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);







        Addreminder firstfragment=new Addreminder();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content_frame, firstfragment).commit();


        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // getSupportActionBar().setTitle(mTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
               // getSupportActionBar().setTitle(mDrawerTitle);
                supportInvalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Reminder");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#56A5EC")));

    }
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // TODO Auto-generated method stub
        String name= parent.getItemAtPosition(position).toString();
        //Toast.makeText(c,name, Toast.LENGTH_SHORT).show();
        selectItem(position,name);

    }

    private void selectItem(int position,String name) {
        // TODO Auto-generated method stub
        if(position==1){

            PendinglistItems firstfragment=new PendinglistItems();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, firstfragment).commit();
            // mDrawerList.setItemChecked(1, true);
            mDrawerLayout.closeDrawer(mDrawerList);
            getSupportActionBar().setTitle(name);
        }else if(position==0){
            Addreminder replacefragment=new Addreminder();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, replacefragment).commit();
            //mDrawerList.setItemChecked(0, true);
            mDrawerLayout.closeDrawer(mDrawerList);
            getSupportActionBar().setTitle(name);
        }else if(position==2){
            RateUs rate=new RateUs();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, rate).commit();
            //mDrawerList.setItemChecked(0, true);
            mDrawerLayout.closeDrawer(mDrawerList);
            getSupportActionBar().setTitle(name);
        }



    }



}
@SuppressLint("ValidFragment")
class PendinglistItems extends Fragment{
    //TextView pendingreminder;
    public ListView pendinglist;
    String idtext="";
    int position;
    PendingRowItem item=null;
    AlertDialog.Builder myAlertDialog;
    List<PendingRowItem> rowItems=null;
    CustomReminderListAdapter adapter;
    String reminderid[]=null,remindertype[]=null,remindertext[]=null,reminderdatetime[] =null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        rowItems = new ArrayList<PendingRowItem>();

        View view= inflater.inflate(R.layout.pendinglistlist, container,false);

        pendinglist=(ListView) view.findViewById(R.id.pendinglist);

        Alarm_Db db=new Alarm_Db(getActivity());
        try {
            db.open();
            db.getData();
            reminderid=db.getReminderId();
            remindertype=db.getReminderType();
            remindertext=db.getReminderText();
            reminderdatetime=db.getReminderDateTime();
            db.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





        if(reminderid.equals("")){
            //doin something when there is no item

        }else
        {

            for(int i=0;i<reminderid.length;i++){

                 item = new PendingRowItem(reminderid[i], remindertype[i],remindertext[i],reminderdatetime[i]);
                rowItems.add(item);
            }
            adapter = new CustomReminderListAdapter(getActivity(),
                    R.layout.pendinglist, rowItems);

            pendinglist.setAdapter(adapter);
            pendinglist.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1,
                                        int pos, long arg3) {
                    // TODO Auto-generated method stub
                    position = pos;
                    TextView text = (TextView) arg1.findViewById(R.id.reminderid);
                    idtext = text.getText().toString();

                    myAlertDialog.show();
                }

            });
        }





        myAlertDialog = new AlertDialog.Builder(getActivity());
        myAlertDialog.setTitle("Delete Reminder");
        myAlertDialog.setMessage("Press Ok to Delete");
        myAlertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {




                AlarmManager alarms = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent ringservice=new Intent("android.intent.Alarm_setter");

                PendingIntent pendingringservice=PendingIntent.getService(getActivity().getApplicationContext(), Integer.parseInt(idtext), ringservice, PendingIntent.FLAG_UPDATE_CURRENT);
                alarms.cancel(pendingringservice);
                pendingringservice.cancel();
                Alarm_Db db=new Alarm_Db(getActivity());
                try{
                    db.open();
                    db.deletereminder(Integer.parseInt(idtext));
                    db.close();
                }catch(Exception e){}

                Toast.makeText(getActivity().getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();
                adapter.remove(adapter.getItem(position));
                adapter.notifyDataSetChanged();

            }});
        myAlertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

                Toast.makeText(getActivity().getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
            }});


        return view;

    }



}


@SuppressLint("ValidFragment")
class Addreminder extends Fragment implements OnClickListener{
    //ImageView cronaldoimage;
    //ExpandableListView ex;
    EditText reminder;
    DatePicker daydate;
    TimePicker daytime;
    TextView setdate,settime,repeattext,selectedloc;
    RadioGroup repeatgroup;
    Button bremind;
    public Addreminder(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view= inflater.inflate(R.layout.addreminder, container,false);
        //cronaldoimage=(ImageView) view.findViewById(R.id.cronaldoimage);
        //ex=(ExpandableListView) view.findViewById(R.id.list);
        //ex.setDividerHeight(2);
        //ex.setClickable(true);
        reminder=(EditText) view.findViewById(R.id.reminder);
        setdate=(TextView) view.findViewById(R.id.setdate);
        settime=(TextView) view.findViewById(R.id.settime);
        repeattext=(TextView) view.findViewById(R.id.repeattext);
        daydate=(DatePicker) view.findViewById(R.id.daydate);
        daytime=(TimePicker) view.findViewById(R.id.daytime);
        repeatgroup=(RadioGroup) view.findViewById(R.id.repeatgroup);
        bremind=(Button) view.findViewById(R.id.bremind);
        bremind.setOnClickListener(this);


        return view;

    }





    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch(v.getId()){
            case R.id.bremind:

                    int radioButtonId=repeatgroup.getCheckedRadioButtonId();
                    //Toast.makeText(getActivity(), "You called me", Toast.LENGTH_SHORT).show();
                    int day=daydate.getDayOfMonth();
                    int month=daydate.getMonth();
                    int year=daydate.getYear();
                    int hour=daytime.getCurrentHour();
                    int minute=daytime.getCurrentMinute();

                    Calendar cal=Calendar.getInstance();
                    cal.set(year, month, day, hour, minute);

                    cal.set(Calendar.SECOND,0);
                    String remindertext=reminder.getText().toString();
                    SharedPreferences alarmprefs=getActivity().getApplicationContext().getSharedPreferences("alarmid", getActivity().getApplicationContext().MODE_PRIVATE);
                    int alarmid=alarmprefs.getInt("alarmid", 1);
                    int forStringMonth=month+1;
                    String datestring= Integer.toString(day)+"/"+Integer.toString(forStringMonth)+"/"+Integer.toString(year);
                    String timestring=Integer.toString(hour)+":"+Integer.toString(minute);


                    AlarmManager alarms = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent ringservice=new Intent("android.intent.Alarm_setter");
                    ringservice.putExtra("remindertext", remindertext);
                    ringservice.putExtra("alarmid", alarmid);
                    PendingIntent pendingringservice=PendingIntent.getService(getActivity().getApplicationContext(), alarmid, ringservice, PendingIntent.FLAG_UPDATE_CURRENT);

                    String alarmtype="0";//0-never 1-fifteen 2-half hour 3-hour 4-daily 5-weekly

                    switch(radioButtonId)
                    {
                        case R.id.repeatnever:
                            //Toast.makeText(getActivity(), "Never", Toast.LENGTH_SHORT).show();
                            alarms.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), pendingringservice);
                            alarmtype="0";
                            break;
                        case R.id.repeatfifteenmin:
                            //Toast.makeText(getActivity(), "Fifteen", Toast.LENGTH_SHORT).show();
                            alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingringservice);
                            //alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),1000, pendingringservice);
                            alarmtype="1";
                            break;
                        case R.id.repeathalfhour:
                            alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HALF_HOUR, pendingringservice);
                            alarmtype="2";
                            break;
                        case R.id.repeathour:
                            alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingringservice);
                            alarmtype="3";
                            break;
                        case R.id.repeatdaily:
                            alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingringservice);
                            alarmtype="4";
                            break;
                        case R.id.repeatweekly:
                            alarms.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY*7, pendingringservice);
                            alarmtype="5";
                            break;
                    }
                    Alarm_Db db=new Alarm_Db(getActivity());
                    try {
                        db.open();
                        db.createentry(alarmid, remindertext, datestring, timestring,alarmtype);
                        db.close();
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    SharedPreferences.Editor edit=alarmprefs.edit();
                    alarmid++;
                    edit.putInt("alarmid", alarmid);
                    edit.commit();
                    Toast.makeText(getActivity(), "Reminder Set", Toast.LENGTH_SHORT).show();



                break;
        }
    }



}
@SuppressLint("ValidFragment")
class RateUs extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
try {
  startActivity(goToMarket);
} catch (ActivityNotFoundException e) {
  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
}

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}


