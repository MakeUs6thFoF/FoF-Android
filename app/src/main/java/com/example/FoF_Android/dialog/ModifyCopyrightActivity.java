package com.example.FoF_Android.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.dialog.model.Copyright;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyCopyrightActivity extends AppCompatActivity {

    RetrofitApi api;
    EditText copyright;
    Button change_btn;
    TokenManager gettoken;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_copyright);
        Intent modifyintent = getIntent();
        Integer memeIdx = modifyintent.getIntExtra("memeIdx",0);

        copyright=(EditText)findViewById(R.id.copyright);
        change_btn=(Button)findViewById(R.id.change_btn);
        change_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String changecopy= copyright.getText().toString();
                HttpClient client = new HttpClient();
                api = client.getRetrofit().create(RetrofitApi.class);
                TokenManager gettoken = new TokenManager(ModifyCopyrightActivity.this);
                String token = gettoken.checklogin(ModifyCopyrightActivity.this);
                api.modifycopy(token,memeIdx).enqueue(new Callback<Copyright>() {
                    @Override
                    public void onResponse(@NonNull Call<Copyright> call, @NonNull Response<Copyright> response) {
                        if (response.isSuccessful()) {
                            Copyright body = response.body();
                            if (body != null) {
                                Log.d("data.getUserId()", body.getMessage() + "");
                                Log.e("patchData end", "======================================");
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Copyright> call, @NonNull Throwable t) {

                    }
                });


            }
        });


}
}
