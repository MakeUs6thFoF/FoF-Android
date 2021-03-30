package com.example.FoF_Android.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    LinearLayout pswd_check;
    TextView minimum_check;
    TextView lower_check;
    TextView number_check;
    TextView special_check;
    TextView login_link;
    EditText ed_email;
    EditText ed_pswd;
    EditText ed_nick;
    Pattern pattern= Patterns.EMAIL_ADDRESS;
    String pswd_pattern="^[a-z0-9~`!@#$%\\^&*()-_]*.{8,20}$"; //수정해야함
    RetrofitApi api;

    boolean flag1, flag2, flag3, flag4;

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
        pswd_check = findViewById(R.id.pswd_check);
        minimum_check = findViewById(R.id.minimum_check);
        lower_check = findViewById(R.id.lower_check);
        number_check = findViewById(R.id.number_check);
        special_check = findViewById(R.id.special_check);

        flag1 = false; flag2 = false; flag3 = false; flag4 = false;

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

                if (pattern.matcher(email).matches() && (flag1 && flag2 && flag3 && flag4)){
                    doSignUp(email, pswd, nickname, api);
                }
                else {
                    Toast.makeText(getApplicationContext(), "양식을 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ed_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if(hasFocus){
                    ed_email.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text = s.toString();
                            if (!pattern.matcher(text).matches()) tv_email_check.setVisibility(View.VISIBLE);
                            else {
                                tv_email_check.setVisibility(View.GONE);
                            }
                        }
                        @Override
                        public void afterTextChanged(Editable s) { }
                    });
                }
                else{
                    tv_email_check.setVisibility(View.GONE);
                }
            }
        });

        ed_pswd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    pswd_check.setVisibility(View.VISIBLE);
                    ed_pswd.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }
                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String text = s.toString();
                            if (!Pattern.matches(pswd_pattern,text))
                                tv_pswd_check.setVisibility(View.VISIBLE);
                            else
                                tv_pswd_check.setVisibility(View.GONE);

                            if (s.length() >= 8) {
                                minimum_check.setTextColor(Color.BLACK);
                                flag1 = true;
                            } else {
                                minimum_check.setTextColor(Color.GRAY);
                                flag1 = false;
                            }

                            if (Pattern.matches("^.*[a-z].*$", s.toString())) {
                                lower_check.setTextColor(Color.BLACK);
                                flag2 = true;
                            } else {
                                lower_check.setTextColor(Color.GRAY);
                                flag2 = false;
                            }

                            if (Pattern.matches("^.*[0-9].*$", s.toString())) {
                                number_check.setTextColor(Color.BLACK);
                                flag3 = true;
                            } else {
                                number_check.setTextColor(Color.GRAY);
                                flag3 = false;
                            }

                            if (Pattern.matches("^.*[~`!@#$%\\^&*()-].*$", s.toString())) {
                                special_check.setTextColor(Color.BLACK);
                                flag4 = true;
                            } else {
                                special_check.setTextColor(Color.GRAY);
                                flag4 = false;
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
                else{
                    System.out.println("여기반짝");
                    pswd_check.setVisibility(View.GONE);
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
                    if(flag == 200){
                        Toast.makeText(getApplicationContext(), "회원가입에 성공했습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUpActivity.this, CongActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else if(flag == 301){
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 302){
                        Toast.makeText(getApplicationContext(), "이메일은 30자리 미만으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 303){
                        Toast.makeText(getApplicationContext(), "이메일 형식을 정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 304){
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 305){
                        Toast.makeText(getApplicationContext(), "비밀번호는 6~20자리를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 306){
                        Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 307){
                        Toast.makeText(getApplicationContext(), "닉네임은 최대 20자리를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 308){
                        Toast.makeText(getApplicationContext(), "중복된 이메일입니다", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 309){
                        Toast.makeText(getApplicationContext(), "중복된 닉네임입니다", Toast.LENGTH_SHORT).show();
                    }
                    else if(flag == 310){
                        Toast.makeText(getApplicationContext(), "타입을 다시 한 번 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
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