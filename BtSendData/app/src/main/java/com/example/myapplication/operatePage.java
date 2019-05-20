package com.example.myapplication;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.charset.Charset;

import static com.example.myapplication.BluetoothConnectionService.write;

public class operatePage extends AppCompatActivity {


    private static final String TAG = "operatePage";
    private ImageButton upward_arrow;
    private ImageButton downward_arrow;
    private ImageButton left_arrow;
    private ImageButton right_arrow;
    private ImageButton stopbutton;
    private ImageButton returnButton;

    public ImageButton getDownwardArrow(){
        return downward_arrow ;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate_page);


        upward_arrow = findViewById(R.id.arrows_UP);
        downward_arrow = findViewById(R.id.arrows_DOWN);
        left_arrow = findViewById(R.id.arrows_LEFT);
        right_arrow = findViewById(R.id.arrows_RIGHT);
        stopbutton = findViewById(R.id.stopButton);
        returnButton = findViewById(R.id.return_button);



        //setting try catch statements in each function so the app doesnt crash when button if disconnected
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(operatePage.this, optionsPage.class);
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



        downward_arrow.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                try {
                    while(downward_arrow.isPressed()) {
                        String driveBackwards = "s";
                        byte[] bytes = driveBackwards.getBytes(Charset.defaultCharset());
                        write(bytes);
                    }
                } catch (Exception e) {
                    toastMessage("Reconnect bluetooth");
                }
            }
        });


        upward_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    while(upward_arrow.isPressed()){
                    String driveForward = "w";
                    byte[] bytes = driveForward.getBytes(Charset.defaultCharset());
                    write(bytes);}
                } catch (Exception e) {
                    toastMessage("Reconnect bluetooth");
                }

            }
        });


        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    while(left_arrow.isPressed()){
                    String driveLeft = "a";
                    byte[] bytes = driveLeft.getBytes(Charset.defaultCharset());
                    write(bytes);}
                } catch (Exception e) {
                    toastMessage("Reconnect bluetooth");
                }
            }
        });


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    while(right_arrow.isPressed()){
                    String driveRight = "d";
                    byte[] bytes = driveRight.getBytes(Charset.defaultCharset());
                    write(bytes);}
                } catch (Exception e) {
                    toastMessage("Reconnect bluetooth");
                }
            }
        });

        stopbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String stop = "x";
                    byte[] bytes = stop.getBytes(Charset.defaultCharset());
                    write(bytes);
                } catch (Exception e) {
                    toastMessage("Reconnect bluetooth");
                }
            }
        });

    }

    //for readability , call this method in methods to avoid passing three arguments each time (just one String message instead)
    public void toastMessage(String message) {
        Toast.makeText(operatePage.this, message, Toast.LENGTH_SHORT).show();
    }
}
