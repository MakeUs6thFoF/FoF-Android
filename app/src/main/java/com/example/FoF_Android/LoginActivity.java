package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView pswd_link;
    TextView signup_link;
    Button login_btn;
    EditText ed_email;
    EditText ed_pswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pswd_link = findViewById(R.id.pswd_link);
        signup_link = findViewById(R.id.signup_link);
        login_btn = findViewById(R.id.login_btn);
        ed_email = findViewById(R.id.ed_email);
        ed_pswd = findViewById(R.id.ed_pswd);

        pswd_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pswdintent = new Intent(LoginActivity.this, PasswordActivity.class);
                startActivity(pswdintent);
            }
        });

        signup_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupintent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(signupintent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 로그인
                String email = ed_email.getText().toString();
                String pswd = ed_pswd.getText().toString();

                Intent categoryintent = new Intent(LoginActivity.this, CategoryActivity.class);
                startActivity(categoryintent);
            }
        });
    }
}