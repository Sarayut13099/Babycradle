package com.example.sarayut.babealert;

import android.support.v4.app.NotificationCompat;

import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationDisplayedResult;
import com.onesignal.OSNotificationReceivedResult;

import java.math.BigInteger;


/**
 * Created by Sarayut on 7/3/2561.
 */

public class NotificationExtenderExample extends NotificationExtenderService {


    protected boolean onNotificationProcessing(OSNotificationReceivedResult receivedResult) {
        OverrideSettings overrideSettings = new OverrideSettings();
        overrideSettings.extender = new NotificationCompat.Extender() {
            @Override
            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
                builder.setSmallIcon(R.drawable.baby_cry);
                return builder.setColor(new BigInteger("FF00FF00",16).intValue());
            }
        };
        OSNotificationDisplayedResult displayedResult = displayNotification(overrideSettings);
        return true;
    }
}
