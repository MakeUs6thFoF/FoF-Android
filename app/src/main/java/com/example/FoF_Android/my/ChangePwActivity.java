package com.example.FoF_Android.my;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.signup.SignUp;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePwActivity extends AppCompatActivity {

    private TextView minimum_checktv;
    private TextView lower_checktv;
    private TextView num_checktv;
    private TextView special_checktv;
    private ImageButton back_bt;
    private EditText pw_et;
    private Button set_bt;

    RetrofitApi api;
    TokenManager gettoken;
    String token;
    boolean flag1, flag2, flag3, flag4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken=new TokenManager(getApplicationContext());
        token = gettoken.checklogin(getApplicationContext());

        minimum_checktv = findViewById(R.id.minimum_check);
        lower_checktv = findViewById(R.id.lower_check);
        num_checktv = findViewById(R.id.num_check);
        special_checktv = findViewById(R.id.special_check);
        back_bt = findViewById(R.id.backBt);
        pw_et = findViewById(R.id.pw_et);
        set_bt = findViewById(R.id.set_bt);

        flag1 = false; flag2 = false; flag3 = false; flag4 = false;

        pw_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() >= 8){
                    minimum_checktv.setTextColor(Color.BLACK);
                    flag1 = true;
                }
                else{
                    minimum_checktv.setTextColor(Color.GRAY);
                    flag1 = false;
                }

                if(Pattern.matches("^.*[a-z].*$", s.toString())){
                    lower_checktv.setTextColor(Color.BLACK);
                    flag2 = true;
                }
                else{
                    lower_checktv.setTextColor(Color.GRAY);
                    flag2 = false;
                }

                if(Pattern.matches("^.*[0-9].*$", s.toString())) {
                    num_checktv.setTextColor(Color.BLACK);
                    flag3 = true;
                }
                else{
                    num_checktv.setTextColor(Color.GRAY);
                    flag3 = false;
                }

                if(Pattern.matches("^.*[~`!@#$%\\^&*()-].*$", s.toString())){
                    special_checktv.setTextColor(Color.BLACK);
                    flag4 = true;
                }
                else{
                    special_checktv.setTextColor(Color.GRAY);
                    flag4 = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        set_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag1 && flag2 && flag3 && flag4){
                    postPw(api);
                }
                else
                    Toast.makeText(getApplicationContext(), "네 조건을 모두 만족해서 작성해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void postPw(RetrofitApi api){
        api.postPw(token, pw_et.getText().toString()).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if(response.isSuccessful()){
                    SignUp body = response.body();
                    if(body.getCode() == 305){
                        Toast.makeText(getApplicationContext(), "비밀번호는 6~20자리로 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                    else if(body.getCode() == 304)
                        Toast.makeText(getApplicationContext(), "비밀번호를 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    else if(body.getCode() == 403)
                        Toast.makeText(getApplicationContext(), "로그인이 되어 있지 않습니다", Toast.LENGTH_SHORT).show();
                    else {
                        Toast.makeText(getApplicationContext(), "비밀번호를 변경하였습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 연결이 원활하지 않습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}