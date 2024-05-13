package com.example.myweatherapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ////TODO use databinding here
        setContentView(R.layout.activity_splash);
        ////TODO please explain what is Handler, Looper.getMainLooper(),
        /*
         Handler allows you to send and process messages and runnables associated with a thread's message queue.
         Looper.getMainLooper() retrieves the Looper for the main thread of the application.
         I have used Handler and Looper.getMainLooper() to post a delayed action on the main thread.
         In this case, it's used to navigate from the splash screen to
          the main activity after a delay of 3000 milliseconds (3 seconds).
         */
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
            },
                SPLASH_DURATION   ////TODO no magic literals //done
        );
    }
}