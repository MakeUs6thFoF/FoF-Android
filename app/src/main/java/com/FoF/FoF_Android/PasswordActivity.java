package com.FoF.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.FoF.FoF_Android.my.CheckCodeActivity;
import com.FoF.FoF_Android.my.EmailAuth;
import com.FoF.FoF_Android.my.MyInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    private TextView email_tv;
    private Button send_bt;
    private ImageButton back_bt;

    RetrofitApi api;
    TokenManager gettoken;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        api = HttpClient.getRetrofit().create(RetrofitApi.class);
        gettoken=new TokenManager(getApplicationContext());
        token = gettoken.checklogin(getApplicationContext());

        email_tv = findViewById(R.id.tv_email);
        send_bt = findViewById(R.id.send_bt);
        back_bt = findViewById(R.id.backBt);

        getInfo(api);

        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(api);
            }
        });

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getInfo(RetrofitApi api){
        api.getMyInfo(token).enqueue(new Callback<MyInfo>() {
            @Override
            public void onResponse(Call<MyInfo> call, Response<MyInfo> response) {
                MyInfo body = response.body();
                String email = body.getData().getUserEmail();
                email_tv.setText(email);
            }

            @Override
            public void onFailure(Call<MyInfo> call, Throwable t) {

            }
        });
    }

    public void sendMail(RetrofitApi api){
        api.getEmailCode(token).enqueue(new Callback<EmailAuth>() {
            @Override
            public void onResponse(Call<EmailAuth> call, Response<EmailAuth> response) {
                if(response.isSuccessful()){
                    EmailAuth body = response.body();
                    Intent intent = new Intent(getApplicationContext(), CheckCodeActivity.class);
                    intent.putExtra("islogin", 1);
                    intent.putExtra("code", body.getNumber());
                    startActivity(intent);
                    finish();
                }
                else
                    Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다. 잠시 후 다시 이용해주세요", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EmailAuth> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "서버와의 통신이 원활하지 않습니다. 잠시 후 다시 이용해주세요", Toast.LENGTH_SHORT).show();
            }
        });
    }
}