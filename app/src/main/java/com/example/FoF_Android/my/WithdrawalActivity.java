package com.example.FoF_Android.my;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.detail.DetailFragment;
import com.example.FoF_Android.detail.SimilarAdapter;
import com.example.FoF_Android.detail.model.Detail;
import com.example.FoF_Android.make.UploadHashtag;
import com.example.FoF_Android.signup.SignUp;
import com.example.FoF_Android.signup.StartActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WithdrawalActivity extends AppCompatActivity {

    private TextView mTextView;
    Button withdrawal_btn;
    TokenManager gettoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        gettoken=new TokenManager(this);
        mTextView = (TextView) findViewById(R.id.text);
        withdrawal_btn=findViewById(R.id.withdrawal_btn);

        withdrawal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpClient client = new HttpClient();
                RetrofitApi api = client.getRetrofit().create(RetrofitApi.class);
                Call<SignUp> call = api.deleteUser(gettoken.checklogin(WithdrawalActivity.this));
                call.enqueue(new Callback<SignUp>() {
                    @Override
                    public void onResponse(Call<SignUp> call, Response<SignUp> response) {
                        if (response.isSuccessful()) {
                          SignUp signUp= response.body();
                            Log.i("TAG", "onResponse: " + response.code());
                          Toast.makeText(WithdrawalActivity.this, "탈퇴가 완료되었습니다", Toast.LENGTH_SHORT).show();
                            gettoken.logout();
                            Intent startintent=new Intent(WithdrawalActivity.this, StartActivity.class);
                            startActivity(startintent);
                        } else
                            Log.i("TAG", "onResponse: " + response.code());
                    }

                    @Override
                    public void onFailure(Call<SignUp> call, Throwable t) {

                        Log.d("MainActivity", t.toString());
                    }
                });
            }
        });

        // Enables Always-on
    }
}