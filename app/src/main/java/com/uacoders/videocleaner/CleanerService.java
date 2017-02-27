package com.uacoders.videocleaner;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Stack;


/**
 * Created by serge on 2/16/17.
 */

public class CleanerService extends IntentService {

    public CleanerService() {
        super("CleanerService");
    }

    public static final String TAG = "Alarm service ";
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do the task here
        Log.i("CleanerService", "Service running");
        ClearFolder(0);
        AlarmReceiver.completeWakefulIntent(intent);
    }

    public void ShowToastInIntentService(final String sText)
    {  final Context MyContext = this;
        new Handler(Looper.getMainLooper()).post(new Runnable()
        {  @Override public void run()
        {  Toast toast1 = Toast.makeText(MyContext, sText, Toast.LENGTH_LONG);
            toast1.show();
        }
        });
    };

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        /*Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);*/
        NotifyByToast(msg);
        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());




    }

    private void NotifyByToast(final String msg){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CleanerService.this.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void ClearFolder(int mode){

        ArrayList<File> files;
        files = getAllFilesInDir(new File("/mnt/extsd/DCIM/VID") );
        files.addAll(getAllFilesInDir(new File("/mnt/extsd/DCIM/LOCK") ));
        files.addAll(getAllFilesInDir(new File("/mnt/extsd/DCIM/PIC") ));
        //files.addAll(getAllFilesInDir(new File("/mnt/sdcard/crash") ));
        String list_files="\n"+files.size();
        list_files +=" \n";
        int deleted = 0;
        for(int i = 0; i < files.size(); i++)
        {
            File file = files.get(i);

            if(file.exists()){
                if(mode==0) {
                    Calendar time = Calendar.getInstance();
                    time.add(Calendar.HOUR_OF_DAY, -12); // so we remove files older then 12 hrs
                    //I store the required attributes here and delete them
                    Date lastModified = new Date(file.lastModified());
                    if (lastModified.before(time.getTime())) {
                        file.delete();
                        list_files += "; \n";
                        list_files += file.getName();
                        deleted ++;
                    }
                } else {
                    deleted ++;
                    file.delete();
                    list_files += "; \n";
                    list_files += file.getName();
                }
            }
        }

        sendNotification("Deletd:" + Integer.toString(deleted) + "files");

    }


    private ArrayList<File> getAllFilesInDir(File dir) {
        if (dir == null)
            return null;

        ArrayList<File> files = new ArrayList<File>();
        Stack<File> dirlist = new Stack<File>();
        dirlist.clear();
        dirlist.push(dir);

        while (!dirlist.isEmpty()) {
            File dirCurrent = dirlist.pop();
            File[] fileList = dirCurrent.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory())
                    dirlist.push(aFileList);
                else
                    files.add(aFileList);
            }
        }
        return files;
    }




}