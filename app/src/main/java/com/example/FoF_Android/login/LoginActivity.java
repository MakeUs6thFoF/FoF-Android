package com.example.FoF_Android.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.FoF_Android.Category.CategoryActivity;
import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.PasswordActivity;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {
    TextView pswd_link;
    TextView signup_link;
    EditText ed_email;
    EditText ed_pswd;
    Button login_btn;

    RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pswd_link = findViewById(R.id.pswd_link);
        signup_link = findViewById(R.id.signup_link);
        login_btn = findViewById(R.id.login_btn);
        ed_email = findViewById(R.id.ed_email);
        ed_pswd = findViewById(R.id.ed_pswd);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);

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
                doLogin(email, pswd, api);

                //TODO 로그인 실패 처리

            }
        });

    }

    public void doLogin(String email, String pswd, RetrofitApi api){
        HashMap<String, Object> input = new HashMap<>();
        input.put("email", email);
        input.put("password", pswd);
        api.postLogin(input).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    Login login = response.body();
                    int flag = login.getCode();
                    System.out.println("확인"+flag);
                    if(flag == 200){
                        System.out.println(login.getJwt().toString());
                        System.out.println("확인"+login.getUserinfo().getIdx().toString());
                        TokenManager token=new TokenManager(getBaseContext());
                        token.createLoginSession(login.getJwt(),login.getUserinfo().getIdx());

                        Intent intent = new Intent(LoginActivity.this, CategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(flag == 302){
                        Toast.makeText(getApplicationContext(), "이메일은 30자리 미만으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 310)
                        Toast.makeText(getApplicationContext(), "아이디를 확인해주세요", Toast.LENGTH_SHORT).show();
                    else if(flag == 312)
                        Toast.makeText(getApplicationContext(), "비활성화 된 계정입니다", Toast.LENGTH_SHORT).show();
                    else if(flag == 313)
                        Toast.makeText(getApplicationContext(), "탈퇴 된 계정입니다", Toast.LENGTH_SHORT).show();
                    else if(flag == 301)
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if(flag == 311){
                        Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    System.out.println(response.toString());
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                System.out.println("안됩니다");
            }
        });
    }
}