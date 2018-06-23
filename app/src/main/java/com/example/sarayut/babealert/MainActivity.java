package com.example.sarayut.babealert;

import com.onesignal.OneSignal;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    public CardView bt_control, bt_video, bt_history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_control = (CardView)findViewById(R.id.bt_control);
        bt_video = (CardView)findViewById(R.id.bt_video);
        bt_history = (CardView)findViewById(R.id.music_bug);

        bt_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Control.class);
                startActivity(intent);
            }
        });

        bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,VideoStreamming.class);
                startActivity(intent);
            }
        });

        bt_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Music.class);
                startActivity(intent);
            }
        });


    }
}
