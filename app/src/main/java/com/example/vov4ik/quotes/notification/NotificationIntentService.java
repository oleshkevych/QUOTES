package com.example.vov4ik.quotes.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.vov4ik.quotes.MainActivity;
import com.example.vov4ik.quotes.Quotes;
import com.example.vov4ik.quotes.QuotesKeeper;
import com.example.vov4ik.quotes.R;

import java.util.List;
import java.util.Random;

/**
 * Created by klogi
 *
 *
 */
public class NotificationIntentService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String ACTION_START = "ACTION_START";
    private static final String ACTION_DELETE = "ACTION_DELETE";
    private static final String EXTRA_NUMBER = "NUMBER_FOR_THE_FIRST_QUOTE";

    public NotificationIntentService() {
        super(NotificationIntentService.class.getSimpleName());
    }

    public static Intent createIntentStartNotificationService(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_START);
        return intent;
    }

    public static Intent createIntentDeleteNotification(Context context) {
        Intent intent = new Intent(context, NotificationIntentService.class);
        intent.setAction(ACTION_DELETE);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(getClass().getSimpleName(), "onHandleIntent, started handling a notification event");
        try {
            String action = intent.getAction();
            if (ACTION_START.equals(action)) {
                processStartNotification();
            }
        } finally {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
    }

    private void processDeleteNotification(Intent intent) {
        // Log something?
    }

    private void processStartNotification() {
        List<Quotes> list;
        int lengthOfTheList = QuotesKeeper.dataLength(getApplicationContext(), "mix");
        Random rand = new Random();
        int[] randomNumber = {rand.nextInt(lengthOfTheList)};
        list = QuotesKeeper.getQuotesList(getApplicationContext(), "mix", 0, randomNumber, 1);

        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder.setContentTitle("Your quote of the day")
                .setAutoCancel(true)
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setContentText(list.get(0).getQuote())
                .setSmallIcon(R.drawable.icon_for_notification);

        Intent mainIntent = new Intent(this, MainActivity.class);
        mainIntent.putExtra(EXTRA_NUMBER, randomNumber[0]);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                NOTIFICATION_ID,
                mainIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setDeleteIntent(NotificationEventReceiver.getDeleteIntent(this));

        final NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(NOTIFICATION_ID, builder.build());
    }
}
