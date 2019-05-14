package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.nio.charset.Charset;

import static com.example.myapplication.BluetoothConnectionService.write;

public class alarmPage extends AppCompatActivity {
    
    private Button btnActivate;

    private final static String TAG = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_page);
        
        btnActivate = (Button)findViewById(R.id.activateButton);
        
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("activated");
                String activateMode = "f";
                byte[] bytes = activateMode.getBytes(Charset.defaultCharset());
                write(bytes);
                Log.d(TAG,"Button f pressed");
            }
        });
    }
    
    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


    }
}
