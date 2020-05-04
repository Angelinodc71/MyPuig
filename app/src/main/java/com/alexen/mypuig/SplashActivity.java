package com.alexen.mypuig;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.alexen.mypuig.api.Connection;

public class SplashActivity extends AppCompatActivity {

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Connection.startConnection();
                Intent intent=new Intent(SplashActivity.this,OnBoardActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);

    }
}
