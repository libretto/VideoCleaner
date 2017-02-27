package com.uacoders.videocleaner;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends Activity {
    AlarmReceiver alarm = new AlarmReceiver();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarm.setAlarm(this);

        /*final Button clear = (Button) findViewById(R.id.cancel_button);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alarm.cancelAlarm(MainActivity.this);
            }
        }) ;*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When the user clicks CANCEL ALARM, cancel the alarm.
            case R.id.cancel_action:
                //alarm.setAlarm(this);
                alarm.cancelAlarm(this);
                return true;
        }
        return false;
    }

    public void NotifyByToast(final String msg){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Setup a recurring alarm every half hour


}
