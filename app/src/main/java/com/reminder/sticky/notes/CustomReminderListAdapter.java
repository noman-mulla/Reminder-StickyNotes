package com.reminder.sticky.notes;

/**
 * Created by noman on 16/02/15.
 */
import java.util.List;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomReminderListAdapter extends ArrayAdapter<PendingRowItem> {

    Context context;

    public CustomReminderListAdapter(Context context, int resourceId,
                                     List<PendingRowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ListViewHolder {
        TextView reminderid,remindertype,remindertext,reminderdatetime;
    }

    public View getView(int position, View convertView, ViewGroup parent) {


            ListViewHolder holder = null;
            PendingRowItem rowItem = getItem(position);

            LayoutInflater mInflater = (LayoutInflater) context
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.pendinglist, null);
                holder = new ListViewHolder();
                holder.reminderid = (TextView) convertView.findViewById(R.id.reminderid);
                holder.remindertype = (TextView) convertView.findViewById(R.id.remindertype);
                holder.remindertext = (TextView) convertView.findViewById(R.id.remindertext);
                holder.reminderdatetime = (TextView) convertView.findViewById(R.id.reminderdatetime);
                convertView.setTag(holder);
            } else
                holder = (ListViewHolder) convertView.getTag();

            holder.reminderid.setText(rowItem.getreminderid());
            holder.remindertype.setText(rowItem.getremindertype());
            holder.remindertext.setText(rowItem.getremindertext());
            holder.reminderdatetime.setText(rowItem.getreminderdatetime());
            return convertView;

    }


}

