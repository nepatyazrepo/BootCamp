package com.nepatyaz.bootcamp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nepatyaz.bootcamp.R;

public class Splash extends AppCompatActivity {

    private String TAG = "Splash Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int delay = 2000;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toMainActivity();
            }
        }, delay);
    }

    private void toMainActivity() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }


}