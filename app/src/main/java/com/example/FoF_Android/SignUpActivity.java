package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    Button signup_btn;
    TextView tv_email_check;
    TextView login_link;
    EditText ed_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn = findViewById(R.id.signup_btn);
        tv_email_check = findViewById(R.id.email_check);
        login_link = findViewById(R.id.login_link);
        ed_email = findViewById(R.id.ed_email);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginintent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginintent);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkEmail(ed_email.getText().toString())) tv_email_check.setVisibility(View.VISIBLE);
                else tv_email_check.setVisibility(View.GONE);
                //TODO 데이터 전달
            }
        });

    }public static boolean checkEmail(String email){

        String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        boolean isNormal = m.matches();
        return isNormal;

    }

}