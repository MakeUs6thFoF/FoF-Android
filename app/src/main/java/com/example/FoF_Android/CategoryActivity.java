package com.example.FoF_Android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Button skip=findViewById(R.id.skip_btn);
        Button next=findViewById(R.id.next_btn);
        Intent mainIntent = new Intent(CategoryActivity.this, MainActivity.class);
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                switch (v.getId()){
                    case R.id.skip_btn:
                        startActivity(mainIntent);
                        break;
                    case R.id.next_btn:
                        //TODO 카테고리 선택 데이터 받아오기
                        startActivity(mainIntent);
                        break;
                }
            }
        };
        skip.setOnClickListener(onClickListener);
        next.setOnClickListener(onClickListener);
    }
}