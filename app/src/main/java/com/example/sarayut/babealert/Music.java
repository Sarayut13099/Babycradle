package com.example.sarayut.babealert;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Music extends AppCompatActivity {
    ImageView imageView;
    FirebaseDatabase database ;
    DatabaseReference musicstatusref;

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_control);

        database = FirebaseDatabase.getInstance();
        musicstatusref = database.getReference("cradle1").child("music").child("status");

        imageView = (ImageView)findViewById(R.id.music_BT);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count == 0){
                    musicstatusref.setValue("play");
                    imageView.setImageResource(R.drawable.pauses);
                    count = 1;
                }else if(count == 1){
                    musicstatusref.setValue("stop");
                    imageView.setImageResource(R.drawable.play);
                    count = 0;
                }

            }
        });
    }
}
