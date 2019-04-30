package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import java.nio.charset.Charset;

public class operatePage extends AppCompatActivity {


    ImageButton upward_arrow;
    ImageButton downward_arrow;
    ImageButton left_arrow;
    ImageButton right_arrow;
    ImageButton stopbutton;
    MainActivity connectBluetoothBetweenActivities = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_page);



        upward_arrow = (ImageButton) findViewById(R.id.arrows_UP);
        downward_arrow = (ImageButton) findViewById(R.id.arrows_DOWN);
        left_arrow = (ImageButton) findViewById(R.id.arrows_LEFT);
        right_arrow = (ImageButton) findViewById(R.id.arrows_RIGHT);
        stopbutton = (ImageButton) findViewById(R.id.stopButton);


        downward_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = "s";
                byte[] bytes = w.getBytes(Charset.defaultCharset());
                connectBluetoothBetweenActivities.mBluetoothConnection.write(bytes);
            }
        });


        upward_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = "w";
                byte[] bytes = w.getBytes(Charset.defaultCharset());
                connectBluetoothBetweenActivities.mBluetoothConnection.write(bytes);
            }
        });



        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = "a";
                byte[] bytes = w.getBytes(Charset.defaultCharset());
                connectBluetoothBetweenActivities.mBluetoothConnection.write(bytes);
            }
        });


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = "d";
                byte[] bytes = w.getBytes(Charset.defaultCharset());
                connectBluetoothBetweenActivities.mBluetoothConnection.write(bytes);
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w = "x";
                byte[] bytes = w.getBytes(Charset.defaultCharset());
                connectBluetoothBetweenActivities.mBluetoothConnection.write(bytes);
            }
        });

    }
}
