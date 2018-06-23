package com.example.sarayut.babealert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.support.v7.widget.SwitchCompat;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onesignal.OneSignal;

import java.io.Console;

public class Control extends AppCompatActivity {

    public Spinner sp_motor, sp_motor_timer;
    public SwitchCompat swMainControl, swMotorControl;
    public String defultTime = "";
    boolean swMainStatus = false;
    public android.support.v7.widget.CardView bt_video, bt_song;

    public TextView tv_babycry_main,tv_babycry_des,tv_babymove,tv_babymove_des;
    public ImageView img_babycry, img_babymove;

    FirebaseDatabase database ;
    DatabaseReference systemStatusRef,motorRef,alert_cry_ref,alert_move_ref, soundStatusRef, motionStatusRef ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        swMotorControl = findViewById(R.id.sw_motor_control);
        tv_babycry_main = findViewById(R.id.text_baby_cry_main);
        tv_babycry_des = findViewById(R.id.text_baby_cry_des);
        img_babycry = findViewById(R.id.img_baby_cry);
        tv_babymove = findViewById(R.id.text_baby_move_main);
        tv_babymove_des = findViewById(R.id.text_baby_move_des);
        img_babymove = findViewById(R.id.img_baby_move);
        bt_video = findViewById(R.id.headContoll);
        bt_song = findViewById(R.id.headContoll2);


        // header button

        bt_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Control.this,VideoStreamming.class);
                startActivity(intent);
            }
        });

        bt_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Control.this,Music.class);
                startActivity(intent);
            }
        });


        if (savedInstanceState != null){
            swMainStatus = savedInstanceState.getBoolean("sw_main");
            defultTime = savedInstanceState.getString("re");
            Log.d("save","notnull");
        }
        else{
            Log.d("save","null");
        }


        //---------------------->>> Firebase <<<---------------------
        database = FirebaseDatabase.getInstance();
        systemStatusRef = database.getReference("cradle1").child("cradleStatus").child("system");
        motorRef = database.getReference().child("cradle1").child("motor");
        alert_cry_ref = database.getReference().child("cradle1").child("alert").child("sound");
        alert_move_ref = database.getReference().child("cradle1").child("alert").child("motion");
        soundStatusRef = database.getReference().child("cradle1").child("cradleStatus").child("sound");
        motionStatusRef = database.getReference().child("cradle1").child("cradleStatus").child("motion");

        // TODO: 1/25/2018 Firebase ReadData
        //------------------>>> Firebase ReadData <<<---------------------
        systemStatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("on")){
                    swMainControl.setChecked(true);
                }
                else if(value.equals("off")){
                    swMainControl.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        motorRef.child("status").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String swMotorStatus = dataSnapshot.getValue(String.class);
                if(swMotorStatus.equals("on")){
                    swMotorControl.setChecked(true);
                }
                else if(swMotorStatus.equals("off")){
                    swMotorControl.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        motorRef.child("level").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("1")){
                    sp_motor.setSelection(1);
                    Log.d("AAA",value);
                }
                else if(value.equals("2")){
                    sp_motor.setSelection(2);
                    Log.d("AAA",value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        motorRef.child("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                switch (value) {
                    case "5":
                        sp_motor_timer.setSelection(1);
                        break;
                    case "10":
                        sp_motor_timer.setSelection(2);
                        break;
                    case "15":
                        sp_motor_timer.setSelection(3);
                        break;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        alert_cry_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("cry")){
                    tv_babycry_main.setText(R.string.baby_cry_header);
                    tv_babycry_des.setText(R.string.baby_cry_des);
                    img_babycry.setImageResource(R.drawable.baby_cry);
                }
                else if(value.equals("noSo")){
                    tv_babycry_main.setText(R.string.baby_cry_header2);
                    tv_babycry_des.setText(R.string.baby_cry_des2);
                    img_babycry.setImageResource(R.drawable.baby_happy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        alert_move_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(value.equals("move")){
                    tv_babymove.setText(R.string.baby_move_header);
                    tv_babymove_des.setText(R.string.baby_move_des);
                    img_babymove.setImageResource(R.drawable.baby_move);
                }
                else if(value.equals("noMo")){
                    tv_babymove.setText(R.string.baby_move_header2);
                    tv_babymove_des.setText(R.string.baby_move_des2);
                    img_babymove.setImageResource(R.drawable.baby_sleepy);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        //------------------->>> SW Main Control <<<---------------------
        swMainControl = findViewById(R.id.sw_main_control);
        swMainControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    Log.d("Switch", "isChecked");
                    systemStatusRef.setValue("on");
                    swMotorControl.setEnabled(true);
                    sp_motor.setEnabled(true);
                    sp_motor_timer.setEnabled(true);
                    soundStatusRef.setValue("on");
                    motionStatusRef.setValue("on");
                }else{
                    systemStatusRef.setValue("off");
                    swMotorControl.setEnabled(false);
                    sp_motor.setEnabled(false);
                    sp_motor_timer.setEnabled(false);
                    soundStatusRef.setValue("off");
                    motionStatusRef.setValue("off");
                }
            }
        });

        //------------------->>> SW Motor Control <<<---------------------



       /* do{
            if(swMotorStatus=="on") Log.d("motorStatus",swMotorStatus);
            else if(swMotorStatus=="off") Log.d("motorStatus",swMotorStatus);
        }
        while (swMotorStatus.equals("0"));*/
        swMotorControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    motorRef.child("status").setValue("on");
                }else{
                    motorRef.child("status").setValue("off");
                }
            }
        });


        //-------------------->>> Motor Level <<<------------------------
        sp_motor = (Spinner) findViewById(R.id.sp_morter);
        ArrayAdapter<CharSequence> moterLvdapter = ArrayAdapter.createFromResource(this,
                R.array.moter_level, android.R.layout.simple_spinner_item);
        moterLvdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_motor.setAdapter(moterLvdapter);

        sp_motor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    Log.d("Sp_Motor",i+"");
                    motorRef.child("level").setValue(i+"");
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //-------------------->>> Motor Timer <<<------------------------
        sp_motor_timer = (Spinner) findViewById(R.id.sp_timer_motor);
        ArrayAdapter<CharSequence> motorTimerAdapter = ArrayAdapter.createFromResource(this,
                R.array.moter_timer, android.R.layout.simple_spinner_item);
        motorTimerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_motor_timer.setAdapter(motorTimerAdapter);

        sp_motor_timer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    if(i == 1) defultTime = "5";
                    else if(i == 2) defultTime = "10";
                    else if(i == 3) defultTime = "15";
                    motorRef.child("time").setValue(defultTime);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Log.d("Test",sp_motor.getSelectedItem().toString());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("sw_main",swMainStatus);
        outState.putString("re",defultTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        swMainStatus = savedInstanceState.getBoolean("sw_main");
        defultTime = savedInstanceState.getString("re");
    }
}
