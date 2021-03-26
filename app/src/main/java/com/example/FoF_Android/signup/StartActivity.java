package com.example.FoF_Android.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.FoF_Android.MainActivity;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.login.LoginActivity;
import com.example.FoF_Android.R;

public class StartActivity extends AppCompatActivity {
    TokenManager tokenManager;
    Button login_btn;
    Button signup_btn;
    Button kakao_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        signup_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);
        tokenManager=new TokenManager(StartActivity.this);
        String token=tokenManager.checklogin(StartActivity.this);
        Integer userIdx = tokenManager.checkIdx(StartActivity.this);

        Log.i("StartActivity", token.toString());
        if(token != null && userIdx != null && token.length()>8){
            Intent mainintent = new Intent(StartActivity.this, MainActivity.class);
            startActivity(mainintent);
            finish();
        } //자동 로그인
        //kakao_login_btn = findViewById(R.id.kakao_login_btn);


        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.signup_btn:
                       Intent signupintent = new Intent(StartActivity.this, SignUpActivity.class);
                       startActivity(signupintent);
                        finish();
                       break;
                    case R.id.login_btn:
                        Intent loginintent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(loginintent);
                        finish();
                        break;

                   // case R.id.kakao_login_btn:
                        //TODO 카카오 로그인
                    //    break;

                }
            }
        };
        signup_btn.setOnClickListener(onClickListener);
        login_btn.setOnClickListener(onClickListener);

     //   kakao_login_btn.setOnClickListener(onClickListener);

    }

}
