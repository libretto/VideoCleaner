package com.uacoders.videocleaner;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.content.WakefulBroadcastReceiver;


/**
 * Created by serge on 2/26/17.
 */

public class AlarmReceiver extends WakefulBroadcastReceiver {

    public static final int REQUEST_CODE = 12345;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, CleanerService.class);
        startWakefulService(context, service);
    }

    public void setAlarm(Context context)
    {
        /*Intent service = new Intent(context, CleanerService.class);
        startWakefulService(context, service);*/

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        /*if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }*/

        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,firstMillis+2000, 10000,                     alarmIntent);
        /*alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstMillis+2000, 30000, alarmIntent);*/

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 2000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);

        //alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,firstMillis+2000, 10000,                     alarmIntent);
        //alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, firstMillis+2000, 30000, alarmIntent);
        /*alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
               SystemClock.elapsedRealtime() + 2000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);*/

        // Enable {@code StartupActivity} to automatically restart the alarm when the
        // device is rebooted.
        ComponentName receiver = new ComponentName(context, StartupActivity.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    public void cancelAlarm(Context context) {

        /*alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, CleanerService.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 2000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);*/

        // If the alarm has been set, cancel it.
        if (alarmMgr!= null) {
            alarmMgr.cancel(alarmIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, StartupActivity.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

}

