package org.szeliga.android.taskerwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import org.szeliga.android.taskerwidget.ActionIntents;
import org.szeliga.android.taskerwidget.R;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link TaskerWidgetConfigureActivity TaskerWidgetConfigureActivity}
 */
public class TaskerWidgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int id : appWidgetIds) {

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_flipper_view);

            // Specify the service to provide data for the collection widget.
            // Note that we need to
            // embed the appWidgetId via the data otherwise it will be ignored.
            final Intent intent = new Intent(context, TaskerWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.page_flipper, intent);

            // Bind the click intent for the next button on the widget
            final Intent nextIntent = new Intent(context,
                    TaskerWidgetProvider.class);
            nextIntent.setAction(ActionIntents.FLIPPER_NEXT_ACTION);
            nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            final PendingIntent nextPendingIntent = PendingIntent
                    .getBroadcast(context, 0, nextIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.next, nextPendingIntent);

            // Bind the click intent for the refresh button on the widget
            final Intent refreshIntent = new Intent(context, TaskerWidgetProvider.class);
            refreshIntent.setAction(ActionIntents.REFRESH_ACTION);
            final PendingIntent refreshPendingIntent = PendingIntent.getBroadcast(context, 0,
                    refreshIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.refresh, refreshPendingIntent);

            appWidgetManager.updateAppWidget(id, rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        if (action.equals(ActionIntents.REFRESH_ACTION)) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, TaskerWidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn), R.id.page_flipper);
        }
        if (action.equals(ActionIntents.FLIPPER_NEXT_ACTION)) {
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_flipper_view);

            rv.showNext(R.id.page_flipper);

            AppWidgetManager.getInstance(context).partiallyUpdateAppWidget(
                    intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID), rv);
        }

        super.onReceive(context, intent);
    }
}

