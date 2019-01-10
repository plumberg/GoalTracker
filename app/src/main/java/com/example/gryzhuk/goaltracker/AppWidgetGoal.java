package com.example.gryzhuk.goaltracker;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import io.paperdb.Paper;

/**
 * Implementation of App Widget functionality.
 */
public class AppWidgetGoal extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {



        Paper.init(context);
        String date = Paper.book().read("Date");
        String desc = Paper.book().read("Goal");

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_goal);
        views.setTextViewText(R.id.appwidget_date, date);
        views.setTextViewText(R.id.appwidget_text, desc);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        /*
        // Create an Intent to launch ExampleActivity
        Intent intent = new Intent(context, AddGoal.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        // Get the layout for the App Widget and attach an on-click listener
        // to the button
        RemoteViews views1 = new RemoteViews(context.getPackageName(), R.layout.activity_add_widget);
        views1.setOnClickPendingIntent(R.id.widget_done_btn, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views1);*/
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
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
}

