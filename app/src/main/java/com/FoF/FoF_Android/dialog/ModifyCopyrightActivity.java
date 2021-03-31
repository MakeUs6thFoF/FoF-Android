package com.FoF.FoF_Android.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.FoF.FoF_Android.HttpClient;
import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;
import com.FoF.FoF_Android.dialog.model.Copyright;
import com.FoF.FoF_Android.make.UploadNextFragment;

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
                api.modifycopy(token,memeIdx, changecopy).enqueue(new Callback<Copyright>() {
                    @Override
                    public void onResponse(@NonNull Call<Copyright> call, @NonNull Response<Copyright> response) {
                        if (response.isSuccessful()) {
                            Copyright body = response.body();
                            if (body != null) {
                                Log.d("data.getUserId()", body.getMessage() + "");
                                Log.e("patchData end", "======================================");
                                Toast.makeText(ModifyCopyrightActivity.this, "저작권자를 수정하였습니다.", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent();
                                intent.putExtra("tagname", changecopy);
                                setResult(UploadNextFragment.RESULT_CODE, intent);
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Copyright> call, @NonNull Throwable t) {

                    }
                });


            }
        });


}   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
