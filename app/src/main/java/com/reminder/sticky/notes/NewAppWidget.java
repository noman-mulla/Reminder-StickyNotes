package com.reminder.sticky.notes;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.graphics.Color;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link NewAppWidgetConfigureActivity NewAppWidgetConfigureActivity}
 */
public class NewAppWidget extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            NewAppWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
            NewAppWidgetConfigureActivity.deleteTitlePref1(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = NewAppWidgetConfigureActivity.loadTitlePref(context, appWidgetId);
        CharSequence color=NewAppWidgetConfigureActivity.loadTitlePref1(context,appWidgetId);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        if(color.equals("RED"))
        views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(255,69,0));
        else if(color.equals("BLUE"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(86,165,236));
        else if(color.equals("YELLOW"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(242,242,78));
        else if(color.equals("GREEN"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(144,238,144));
        else if(color.equals("PURPLE"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(179,116,225));
        else if(color.equals("ORANGE"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(249,150,107));
        else if(color.equals("BROWN"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(225,158,91));
        else if(color.equals("PINK"))
            views.setInt(R.id.appwidget_text,"setBackgroundColor",Color.rgb(227,138,174));
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


