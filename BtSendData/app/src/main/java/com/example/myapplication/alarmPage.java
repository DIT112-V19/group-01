package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.nio.charset.Charset;

import static com.example.myapplication.BluetoothConnectionService.write;

public class alarmPage extends AppCompatActivity {

    private Button btnActivate;
    private ImageButton returnButton;

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
                Intent intent = new Intent(alarmPage.this,optionsPage.class);
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
    }

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }
}
