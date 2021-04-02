package com.FoF.FoF_Android;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.FoF.FoF_Android.signup.StartActivity;

public class SplashActivity extends AppCompatActivity {
    TokenManager tokenManager;
    Intent mainintent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tokenManager=new TokenManager(this);
        String token=tokenManager.checklogin(this);
        Integer userIdx = tokenManager.checkIdx(this);
        if(token != null && userIdx != null && token.length()>8){
            mainintent = new Intent(SplashActivity.this, MainActivity.class);
        } //자동 로그인
        else{
            mainintent = new Intent(SplashActivity.this, StartActivity.class);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(mainintent);
                finish();
            }
        },1000);
}
}
