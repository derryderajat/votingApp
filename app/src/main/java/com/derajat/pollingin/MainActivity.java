package com.derajat.pollingin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    Handler handler = new Handler();
    private final int interval = 1000; // 1 Second
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // do something
                Intent intent = new Intent(MainActivity.this, Home.class);
                // If you just use this that is not a valid context. Use ActivityName.this
                startActivity(intent);
                finish();
            }
        }, 2000);

    }
}