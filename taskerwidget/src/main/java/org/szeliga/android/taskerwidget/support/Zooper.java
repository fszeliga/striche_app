package org.szeliga.android.taskerwidget.support;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.szeliga.android.taskerwidget.ActionIntents;

/**
 * Created by Filip on 7/7/2016.
 */
public class Zooper {

    public static void sendUpdateRequest(Activity a, String zoopervar, String zoopertext){
        Intent zooperIntent = new Intent();
        zooperIntent.setAction(ActionIntents.ZOOPER_BROADCAST_INTENT);

        Bundle bundle = new Bundle();
        bundle.putInt(ActionIntents.ZOOPER_VERSION_CODE, 1);
        bundle.putString(ActionIntents.ZOOPER_STRING_VAR, zoopervar);
        bundle.putString(ActionIntents.ZOOPER_STRING_TEXT, zoopertext);
        zooperIntent.putExtra(ActionIntents.ZOOPER_BUNDLE_EXTRA_KEY, bundle);

        a.sendBroadcast(zooperIntent);
    }
}
