package com.example.smartbabymonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;

/**
 * Created by Yamina Santillan
 * Splash Screen Activity which contains a nice intro to the baby app.
 *
 */

public class SplashScreen extends AppCompatActivity {
    private static final int DELAY_MILLISECONDS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, Registration.class);
                startActivity(i);
//                finish();
            }
        }, DELAY_MILLISECONDS);
    }
}
