package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FoF_Android.my.CheckCodeActivity;
import com.example.FoF_Android.my.EmailAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestPwActivity extends AppCompatActivity {

    private ImageButton backBt;
    private EditText et_email;
    private Button send_bt;
    private TextView login;

    RetrofitApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_pw);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);

        backBt = findViewById(R.id.backBt);
        et_email = findViewById(R.id.et_email);
        send_bt = findViewById(R.id.send_bt);
        login = findViewById(R.id.login);

        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(api);
            }
        });
    }

    public void sendMail(RetrofitApi api){
        api.getGuestEmailCode(et_email.getText().toString()).enqueue(new Callback<EmailAuth>() {
            @Override
            public void onResponse(Call<EmailAuth> call, Response<EmailAuth> response) {
                if(response.isSuccessful()){
                    EmailAuth body = response.body();
                    if(body.getCode() == 200){
                        Toast.makeText(getApplicationContext(), "인증번호를 발송했습니다", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), CheckCodeActivity.class);
                        intent.putExtra("islogin",2); //2면 게스트 1이면 로그인
                        intent.putExtra("guestEmail", et_email.getText().toString());
                        intent.putExtra("code", body.getNumber());
                        startActivity(intent);
                        finish();
                    }
                    else if(body.getCode() == 300){
                        Toast.makeText(getApplicationContext(), "발송에 실패하였습니다. 다시 한번 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(body.getCode() == 301){
                        Toast.makeText(getApplicationContext(), "이메일을 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(body.getCode() == 302){
                        Toast.makeText(getApplicationContext(), "이메일은 30자리 미만으로 입력해주세요",Toast.LENGTH_SHORT).show();
                    }
                    else if(body.getCode() == 303){
                        Toast.makeText(getApplicationContext(), "이메일 형식을 정확하게 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                    else if(body.getCode() == 310){
                        Toast.makeText(getApplicationContext(), "이메일 타입을 다시 한 번 확인해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<EmailAuth> call, Throwable t) {

            }
        });
    }
}