package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;

import static com.example.myapplication.BluetoothConnectionService.write;

public class optionsPage extends AppCompatActivity {

    public Button alarm;
    public Button play;



public static final String TAG = "optionsPage";

    public void initialize(){
        play = (Button) findViewById(R.id.play);
        alarm = (Button) findViewById(R.id.alarm);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)  {

                try {
                    String playOperateManually = "o";
                    byte[] bytes = playOperateManually.getBytes(Charset.defaultCharset());
                    write(bytes);
                    Log.d(TAG, "char o sent to arduino mega");
                    Intent intent = new Intent(optionsPage.this,operatePage.class);
                    startActivity(intent);

                }
                catch (Exception e) {
                    Toast.makeText(optionsPage.this, "not connected", Toast.LENGTH_SHORT).show();
                    e.getMessage();

                }


            }
        });
        alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(optionsPage.this,alarmPage.class);
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