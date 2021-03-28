package com.example.FoF_Android.my;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.signup.SignUp;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInfoActivity extends AppCompatActivity {

    private EditText nick_ed;
    private EditText email_ed;
    private Button finish_bt;
    private ImageButton back_bt;

    RetrofitApi api;
    TokenManager gettoken;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_info);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken=new TokenManager(getApplicationContext());
        token = gettoken.checklogin(getApplicationContext());

        nick_ed = findViewById(R.id.ed_nick);
        email_ed = findViewById(R.id.ed_email);
        finish_bt = findViewById(R.id.finish_btn);
        back_bt = findViewById(R.id.back);

        finish_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInfo(api);
            }
        });

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void changeInfo(RetrofitApi api){
        HashMap<String, Object> input = new HashMap<>();
        input.put("email", email_ed.getText().toString());
        input.put("nickname", nick_ed.getText().toString());
        api.postInfo(token, input).enqueue(new Callback<SignUp>() {
            @Override
            public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                if(response.isSuccessful()){
                    SignUp body = response.body();
                    int code = body.getCode();
                    if(code == 200)
                    {
                        Toast.makeText(getApplicationContext(), "개인정보 변경을 완료했습니다", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if(code == 320)
                        Toast.makeText(getApplicationContext(), "이메일 혹은 닉네임 중 한가지는 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if(code == 310)
                        Toast.makeText(getApplicationContext(), "이메일 타입을 확인해주세요", Toast.LENGTH_SHORT).show();
                    else if(code == 311)
                        Toast.makeText(getApplicationContext(), "닉네임 타입을 확인해주세요", Toast.LENGTH_SHORT).show();
                    else if(code == 302)
                        Toast.makeText(getApplicationContext(), "이메일은 30자리 미만으로 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if(code == 303)
                        Toast.makeText(getApplicationContext(), "이메일 형식을 정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    else if(code == 307)
                        Toast.makeText(getApplicationContext(), "닉네임은 최대 20자리를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다. 잠시 후 다시 시도해주세요",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SignUp> call, Throwable t) {
                System.out.println("여기2");
            }
        });
    }
}