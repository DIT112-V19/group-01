package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.BluetoothConnectionService.write;

public class alarmPage extends AppCompatActivity {

    private Button btnActivate;
    private ImageButton returnButton;
    private TimePicker alarmTime;

    SimpleDateFormat simpleDateFormat;
    Calendar calender;

    private final static String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);

        btnActivate = (Button) findViewById(R.id.activateButton);
        returnButton = (ImageButton) findViewById(R.id.return_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(alarmPage.this, optionsPage.class);
                startActivity(intent);
            }
        });

        //try catch to make sure that the app doesn't crash
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                String activateMode = "f";
                byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                write(bytes);
                Log.d(TAG, "Button f pressed");
                   }
                catch (Exception e){
                    toastMessage("Reconnect bluetooth");
                }
            }
        });

        //alarm time refers to the time-picker, it is set to a 24 hour format
        alarmTime = findViewById(R.id.timePicker);
        alarmTime.setIs24HourView(true);

        Timer t = new Timer();
        //this method is used to schedule an event
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                calender = Calendar.getInstance();
                simpleDateFormat = new SimpleDateFormat("HH:mm");

                //this method compares if the current time is the same like the time set on the time picker, implementation of the button "set alarm" would be possible by
                //the use of a boolean
                if (simpleDateFormat.format(calender.getTime()).equals(AlarmTime())) {
                    //toastMessage("activated");
                    String activateMode = "f";
                    byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                    write(bytes);
                    Log.d(TAG, "Button f pressed");
                }
            }
            //delay = when does it start comparing, period = how often does it compare, 1000 ms = 1 sec
        }, 0, 1000);
    }
    public String AlarmTime(){

        int alarmHours = alarmTime.getHour();
        int alarmMinutes = alarmTime.getMinute();

        String stringAlarmTime;
        String stringAlarmMinutes;

        if(alarmMinutes < 10) {
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(Integer.toString(alarmMinutes));
        } else {
            stringAlarmMinutes = Integer.toString(alarmMinutes);
        }

        stringAlarmTime = Integer.toString(alarmHours).concat(":").concat(stringAlarmMinutes);

        return stringAlarmTime;
    }



    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
