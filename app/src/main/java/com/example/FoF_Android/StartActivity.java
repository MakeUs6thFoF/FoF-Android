package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button signup_btn=findViewById(R.id.signup_btn);
        Button login_btn=findViewById(R.id.login_btn);
        Button kakao_login_btn=findViewById(R.id.kakao_login_btn);
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.signup_btn:
                       Intent signupintent = new Intent(StartActivity.this,SignUpActivity.class);
                       startActivity(signupintent);
                        break;
                    //두번째 버튼 행동
                    case R.id.login_btn:
                        Intent loginintent = new Intent(StartActivity.this,LoginActivity.class);
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
