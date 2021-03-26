package com.example.FoF_Android.dialog.model;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.FoF_Android.HttpClient;
import com.example.FoF_Android.R;
import com.example.FoF_Android.RetrofitApi;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.signup.SignUp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelDialog extends Dialog {

    private Button mPositiveButton;
    private Button mNegativeButton;
    private Integer memeidx;
    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;

    RetrofitApi api;
    TokenManager gettoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_delete);

        //셋팅
        mPositiveButton=(Button)findViewById(R.id.delete);
        mNegativeButton=(Button)findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    //생성자 생성
    public CancelDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

    }


}

