package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.charset.Charset;

import static com.example.myapplication.BluetoothConnectionService.write;

public class operatePage extends AppCompatActivity {


    ImageButton upward_arrow;
    ImageButton downward_arrow;
    ImageButton left_arrow;
    ImageButton right_arrow;
    ImageButton stopbutton;
    MainActivity mBluetoothConnection = new MainActivity();

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
                String driveBackwards = "s";
                byte[] bytes = driveBackwards.getBytes(Charset.defaultCharset());
                write(bytes);
            }
        });


        upward_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driveForward = "w";
                byte[] bytes = driveForward.getBytes(Charset.defaultCharset());
                write(bytes);
            }
        });



        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driveLeft = "a";
                byte[] bytes = driveLeft.getBytes(Charset.defaultCharset());
                write(bytes);
            }
        });


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String driveRight = "d";
                byte[] bytes = driveRight.getBytes(Charset.defaultCharset());
                write(bytes);
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stop = "x";
                byte[] bytes = stop.getBytes(Charset.defaultCharset());
                write(bytes);
            }
        });

    }
}
