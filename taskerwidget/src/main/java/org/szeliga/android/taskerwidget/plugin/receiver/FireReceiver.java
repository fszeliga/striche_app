/*
 * Copyright 2013 two forty four a.m. LLC <http://www.twofortyfouram.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * <http://www.apache.org/licenses/LICENSE-2.0>
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

package org.szeliga.android.taskerwidget.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.szeliga.android.taskerwidget.plugin.Constants;
import org.szeliga.android.taskerwidget.plugin.bundle.BundleScrubber;
import org.szeliga.android.taskerwidget.plugin.bundle.PluginBundleManager;
import org.szeliga.android.taskerwidget.plugin.ui.EditActivity;
import org.szeliga.android.taskerwidget.plugin.vendor.TaskerPlugin;

import java.util.Locale;

/**
 * This is the "fire" BroadcastReceiver for a Locale Plug-in setting.
 *
 * @see com.twofortyfouram.locale.Intent#ACTION_FIRE_SETTING
 * @see com.twofortyfouram.locale.Intent#EXTRA_BUNDLE
 */
public final class FireReceiver extends BroadcastReceiver
{

    /**
     * @param context {@inheritDoc}.
     * @param intent the incoming {@link com.twofortyfouram.locale.Intent#ACTION_FIRE_SETTING} Intent. This
     *            should contain the {@link com.twofortyfouram.locale.Intent#EXTRA_BUNDLE} that was saved by
     *            {@link EditActivity} and later broadcast by Locale.
     */
    @Override
    public void onReceive(final Context context, final Intent intent)
    {
        /*
         * Always be strict on input parameters! A malicious third-party app could send a malformed Intent.
        */
        Log.d("Received action",intent.getAction());
        Bundle bun = intent.getExtras();
        if (bun.keySet().isEmpty()){
            Log.d("[Logging i.getExtras()]", "empty");
        }
        for (String key : bun.keySet()) {
            Object value = bun.get(key);
            Log.d("[Logging i.getExtras()]", String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }

        if (!com.twofortyfouram.locale.Intent.ACTION_FIRE_SETTING.equals(intent.getAction()))
        {
            if (Constants.IS_LOGGABLE)
            {
                Log.e(Constants.LOG_TAG,
                      String.format(Locale.US, "Received unexpected Intent action %s", intent.getAction())); //$NON-NLS-1$
            }
            return;
        }

        BundleScrubber.scrub(intent);



        final Bundle bundle = intent.getBundleExtra(com.twofortyfouram.locale.Intent.EXTRA_BUNDLE);
        BundleScrubber.scrub(bundle);
        setTaskerVars(intent);
        if (PluginBundleManager.isBundleValid(bundle))
        {
            final String message = bundle.getString(PluginBundleManager.BUNDLE_EXTRA_STRING_MESSAGE);
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Bundle invalid", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    To be used in onReceive
     */
    private void setTaskerVars(Intent intent){
        if ( isOrderedBroadcast() )  {

            setResultCode( TaskerPlugin.Setting.RESULT_CODE_OK );

            if ( TaskerPlugin.Setting.hostSupportsVariableReturn( intent.getExtras() ) ) {
                Log.d("[FireReceiver]","Setting var %pcolour");
                Bundle vars = new Bundle();
                vars.putString( "%pcolour", "boo" );

                TaskerPlugin.addVariableBundle( getResultExtras( true ), vars );
            }
        }
    }
}