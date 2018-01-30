package com.example.sarayut.babealert;

import android.content.ComponentName;
import android.net.Uri;
import android.support.customtabs.CustomTabsCallback;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

public class VideoStreamming extends AppCompatActivity {

    public VideoView videoView;

    private CustomTabsServiceConnection customTabsServiceConnection;
    private CustomTabsSession customTabsSession;
    private Uri uri = Uri.parse("http://192.168.43.52:8081");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_streamming);

        openCustomTabs();
        connectCustomTabsService();
        finish();

    }
    public void connectCustomTabsService() {
        String chromePackageName = "com.android.chrome";
        customTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                // หยุดเชื่อมต่อ Custom Tabs Service
            }

            @Override
            public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
                // เชื่อมต่อกับ Custom Tabs Service ได้แล้ว
                createCustomTabsSession(customTabsClient);
            }
        };
        CustomTabsClient.bindCustomTabsService(this, chromePackageName, customTabsServiceConnection);
    }

    public void createCustomTabsSession(CustomTabsClient customTabsClient) {
        customTabsSession = customTabsClient.newSession(new CustomTabsCallback() {
            @Override
            public void onNavigationEvent(int navigationEvent, Bundle extras) {
                // เมื่อมี Navigation Event ใดๆเกิดขึ้นบน Custom Tabs
            }
        });
    }

    public void openCustomTabs() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, uri);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (customTabsServiceConnection != null)
            unbindService(customTabsServiceConnection);
    }


}
