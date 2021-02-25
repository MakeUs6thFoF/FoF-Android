package com.example.FoF_Android.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.FoF_Android.LoginActivity;
import com.example.FoF_Android.R;

public class StartActivity extends AppCompatActivity {

    Button login_btn;
    Button signup_btn;
    Button kakao_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        signup_btn = findViewById(R.id.signup_btn);
        login_btn = findViewById(R.id.login_btn);
        kakao_login_btn = findViewById(R.id.kakao_login_btn);

        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.signup_btn:
                       Intent signupintent = new Intent(StartActivity.this, SignUpActivity.class);
                       startActivity(signupintent);
                        break;
                    case R.id.login_btn:
                        Intent loginintent = new Intent(StartActivity.this, LoginActivity.class);
                        startActivity(loginintent);
                        break;
                    case R.id.kakao_login_btn:
                        //TODO 카카오 로그인
                        break;
                }
            }
        };
        signup_btn.setOnClickListener(onClickListener);
        login_btn.setOnClickListener(onClickListener);
        kakao_login_btn.setOnClickListener(onClickListener);
    }

}
