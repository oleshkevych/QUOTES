package com.example.vov4ik.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.vov4ik.quotes.notification.NotificationEventReceiver;

/**
 * Created by vov4ik on 7/24/2016.
 */
public class NotificationForActivity  extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

    public void onSendNotificationsButtonClick(View view) {
        NotificationEventReceiver.setupAlarm(getApplicationContext());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        // handleIntent();
    }
}

