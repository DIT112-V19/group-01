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

public class SetAlarm extends AppCompatActivity {

    private Button btnActivate;
    private ImageButton returnButton;
    private Button setAlarmButton;
    private TimePicker alarmTime;
    boolean alarmHasBeenSet = false;
    boolean alarmPlayed = false;

    SimpleDateFormat simpleDateFormat;
    Calendar calender;

    private final static String TAG = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        btnActivate = findViewById(R.id.activateButton);
        returnButton = findViewById(R.id.return_button);
        setAlarmButton = findViewById(R.id.set_Alarm_button);

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetAlarm.this, UserOptions.class);
                startActivity(intent);

                try {
                    String exitManualControl = "v";
                    byte[] bytes = exitManualControl.getBytes(Charset.defaultCharset());
                    write(bytes);
                } catch (Exception e) {
                    Log.d(TAG, "Not connected by bluetooth");
                }
            }
        });

        setAlarmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmHasBeenSet = true;
            }
        });

        //try catch to make sure that the app doesn't crash
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String activateMode = "f";
                    byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                    write(bytes);
                    Log.d(TAG, "Button f pressed");
                } catch (Exception e) {
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
                if (alarmHasBeenSet && simpleDateFormat.format(calender.getTime()).equals(AlarmTime()) && !alarmPlayed) {

                    String activateMode = "f";
                    byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                    write(bytes);
                    Log.d(TAG, "Button f pressed");
                    alarmPlayed = true;
                }
            }
            //delay = when does it start comparing, period = how often does it compare, 1000 ms = 1 sec
        }, 0, 1000);
    }

    public String AlarmTime() {

        int alarmHours = alarmTime.getHour();
        int alarmMinutes = alarmTime.getMinute();

        String stringAlarmTime;
        String stringAlarmMinutes;
        String stringAlarmHours;

        if (alarmMinutes < 10) {
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(Integer.toString(alarmMinutes));
        } else {
            stringAlarmMinutes = Integer.toString(alarmMinutes);
        }

        if (alarmHours < 10) {
            stringAlarmHours = "0";
            stringAlarmHours = stringAlarmHours.concat(Integer.toString(alarmHours));
        } else {
            stringAlarmHours = Integer.toString(alarmHours);
        }

        stringAlarmTime = stringAlarmHours.concat(":").concat(stringAlarmMinutes);

        return stringAlarmTime;
    }


    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
