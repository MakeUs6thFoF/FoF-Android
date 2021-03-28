package com.example.FoF_Android.my;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;

public class CheckCodeActivity extends AppCompatActivity {

    private EditText code_et;
    private Button continue_bt;
    private ImageButton back_bt;

    RetrofitApi api;
    TokenManager gettoken;
    String token;
    int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);

        code = getIntent().getIntExtra("code", 1);

        code_et = findViewById(R.id.et_code);
        back_bt = findViewById(R.id.backBt);
        continue_bt = findViewById(R.id.send_bt);

        continue_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(code_et.getText().toString()) == code){
                    Toast.makeText(getApplicationContext(), "인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), ChangePwActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"인증번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}