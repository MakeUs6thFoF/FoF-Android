package com.example.FoF_Android.my;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.FoF_Android.R;

public class WithdrawalActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);

        mTextView = (TextView) findViewById(R.id.text);

        // Enables Always-on
    }
}