package com.example.FoF_Android.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.FoF_Android.CongActivity;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.login.LoginActivity;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;

import java.util.HashMap;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    Button signup_btn;
    TextView tv_email_check;
    TextView tv_pswd_check;
    TextView login_link;
    EditText ed_email;
    EditText ed_pswd;
    EditText ed_nick;
    Pattern pattern= Patterns.EMAIL_ADDRESS;
    String pswd_pattern="^[A-Za-z0-9_@./#&+-]*.{8,20}$"; //수정해야함
    RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signup_btn = findViewById(R.id.signup_btn);
        tv_email_check = findViewById(R.id.email_check);
        tv_pswd_check = findViewById(R.id.paswd_check);
        login_link = findViewById(R.id.login_link);
        ed_email = findViewById(R.id.ed_email);
        ed_pswd = findViewById(R.id.ed_pswd);
        ed_nick = findViewById(R.id.ed_nick);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);

        login_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginintent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginintent);
            }
        });
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = ed_email.getText().toString();
                String pswd = ed_pswd.getText().toString();
                String nickname = ed_nick.getText().toString();


                if (!pattern.matcher(email).matches()) ;
                else {
                    tv_email_check.setVisibility(View.GONE);
                    doSignUp(email, pswd, nickname, api);
                }
                //TODO 데이터 전달
            }
        });
        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = ed_email.getText().toString();
                if (!pattern.matcher(email).matches()) tv_email_check.setVisibility(View.VISIBLE);
                else {
                    tv_email_check.setVisibility(View.GONE);
                }
            }
        });
        ed_pswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = ed_email.getText().toString();
                if (!pattern.matcher(pswd_pattern).matches()) tv_pswd_check.setVisibility(View.VISIBLE);
                else {
                    tv_pswd_check.setVisibility(View.GONE);
                }
            }
        });
    }


    public void doSignUp(String email, String pswd, String nickname, RetrofitApi api){
        HashMap<String, Object> input = new HashMap<>();
        input.put("email", email);
        input.put("password", pswd);
        input.put("nickname", nickname);
        api.postSignUp(input).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if (response.isSuccessful()){
                    SignUp signup = response.body();
                    int flag = signup.getCode();
                    System.out.println("확인"+signup.getCode());
                    Intent intent = new Intent(SignUpActivity.this, CongActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                    System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {

            }
        });
    }

}