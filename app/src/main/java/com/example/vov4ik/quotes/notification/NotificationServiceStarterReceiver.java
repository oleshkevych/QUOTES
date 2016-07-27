package com.example.vov4ik.quotes.notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**

 *
 * Broadcast receiver for: BOOT_COMPLETED, TIMEZONE_CHANGED, and TIME_SET events. Sets Alarm Manager for notification;
 */
public final class NotificationServiceStarterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationEventReceiver.setupAlarm(context);


    }
}