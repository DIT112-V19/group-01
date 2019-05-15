package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextClock;
import android.widget.TimePicker;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.myapplication.BluetoothConnectionService.write;

public class alarmPage extends AppCompatActivity {

    private Button btnActivate;
    private ImageButton returnButton;
    private TimePicker alarmTime;
    private TextClock currentTime;

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


        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("activated");
                String activateMode = "f";
                byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                write(bytes);
                Log.d(TAG, "Button f pressed");
            }
        });

        alarmTime = findViewById(R.id.timePicker);
        currentTime = findViewById(R.id.textClock);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentTime.getText().toString().equals(AlarmTime())) {

                }
            }
        }, 0, 1000);
    }
    public String AlarmTime(){
        Calendar rightNow = Calendar.getInstance();
        //this is 12 hour format, change to 24 hour format is possible
        int alarmHours = rightNow.get(Calendar.HOUR_OF_DAY);
        int alarmMinutes = rightNow.get(Calendar.MINUTE);

        String stringAlarmTime;


        if(alarmHours > 12){
            alarmHours = alarmHours - 12;
            stringAlarmTime = Integer.toString(alarmHours).concat(":").concat(Integer.toString(alarmMinutes).concat(("PM")));
        } else {
            stringAlarmTime = Integer.toString(alarmHours).concat(":").concat(Integer.toString(alarmMinutes).concat("AM"));
        }

        return stringAlarmTime;

    }



    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }

}
