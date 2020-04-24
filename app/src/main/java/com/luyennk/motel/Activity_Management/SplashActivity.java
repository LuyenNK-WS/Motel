package com.luyennk.motel.Activity_Management;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.luyennk.motel.R;

public class SplashActivity extends Activity {

    final long delay=2000;

    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            Intent intent =new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);

            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler=new Handler();
        handler.postDelayed(runnable,delay);
    }
}
