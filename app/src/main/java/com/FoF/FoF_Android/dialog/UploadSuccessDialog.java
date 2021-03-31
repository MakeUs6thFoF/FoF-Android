package com.FoF.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.TokenManager;

public class UploadSuccessDialog extends Dialog {

    private Button mPositiveButton;
    private Button mNegativeButton;


    TokenManager gettoken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_upload_success);

        mPositiveButton = (Button) findViewById(R.id.confirm);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }


    //생성자 생성

    public UploadSuccessDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

    }




}

