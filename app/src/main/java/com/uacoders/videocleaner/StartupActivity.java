package com.uacoders.videocleaner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by serge on 2/26/17.
 */

public class StartupActivity extends BroadcastReceiver {
    AlarmReceiver alarm = new AlarmReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {

        //Toast.makeText(context, "Run on startup? Intent Detected.", Toast.LENGTH_LONG).show();
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            alarm.setAlarm(context);
        }
    }

}
