package org.szeliga.android.taskerwidget.widget;

/**
 * Created by Filip on 7/6/2016.
*/

import java.text.SimpleDateFormat;
import java.util.Date;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import org.szeliga.android.taskerwidget.R;

public class TaskerWidgetService extends RemoteViewsService {

    private static final String TAG = TaskerWidgetService.class.getSimpleName();

    final static int[] mLayoutIds = { R.layout.page1, R.layout.page2 };

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory()");

        return new ViewFactory(intent);
    }

    private class ViewFactory implements RemoteViewsService.RemoteViewsFactory {

        private int mInstanceId = AppWidgetManager.INVALID_APPWIDGET_ID;
        private Date mUpdateDate = new Date();

        public ViewFactory(Intent intent) {
            mInstanceId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID );
        }

        @Override
        public void onCreate() {
            Log.i(TAG, "onCreate()");

        }

        @Override
        public void onDataSetChanged() {
            Log.i(TAG, "onDataSetChanged()");
            //Signals that something happended...this case refresh was clicked
            mUpdateDate = new Date();
        }

        @Override
        public void onDestroy() {
            Log.i(TAG, "onDestroy()");
        }

        @Override
        public int getCount() {
            Log.i(TAG, "getCount() " + mLayoutIds.length);

            return mLayoutIds.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.i(TAG, "getViewAt()" + position);

            /*
            * TODO
            * HERE IS WHERE THE PAGE NUMBER IS PICKED
            */

            RemoteViews page = new RemoteViews(getPackageName(), mLayoutIds[position]);
            SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance();
            page.setTextViewText(R.id.update_date, sdf.format(mUpdateDate));

            return page;
        }

        @Override
        public RemoteViews getLoadingView() {
            Log.i(TAG, "getLoadingView()");

            return new RemoteViews(getPackageName(), R.layout.loading);
        }

        @Override
        public int getViewTypeCount() {
            Log.i(TAG, "getViewTypeCount()");

            return mLayoutIds.length;
        }

        @Override
        public long getItemId(int position) {
            Log.i(TAG, "getItemId()");

            return position;
        }

        @Override
        public boolean hasStableIds() {
            Log.i(TAG, "hasStableIds()");

            return true;
        }

    }

}