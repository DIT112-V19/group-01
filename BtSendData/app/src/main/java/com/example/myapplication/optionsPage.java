package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class optionsPage extends AppCompatActivity {

    public Button alarm;
    public Button play;
    public Button blueToothPage;


    public void initialize(){
        play = (Button) findViewById(R.id.play);
        alarm = (Button) findViewById(R.id.alarm);
        blueToothPage = (Button) findViewById(R.id.bluetooth);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(optionsPage.this,operatePage.class);
                startActivity(intent);
            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(optionsPage.this,alarmPage.class);
                startActivity(intent);
            }
        });
        blueToothPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(optionsPage.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_page);

        initialize();
    }
}