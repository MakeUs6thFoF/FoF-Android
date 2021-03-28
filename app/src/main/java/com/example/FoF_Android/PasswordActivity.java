package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.FoF_Android.my.MyInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PasswordActivity extends AppCompatActivity {

    private TextView email_tv;
    private Button send_bt;

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

        getInfo(api);

        send_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail(api);
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

    }
}