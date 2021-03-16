package com.example.FoF_Android.home.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.FoF_Android.R;

public class SelectDialog extends Dialog {

    private Button mModifyButton;
    private Button mNegativeButton;

    private View.OnClickListener mModifyListener;
    private View.OnClickListener mNegativeListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_select);

        //셋팅
        mModifyButton=(Button)findViewById(R.id.delete);
        mNegativeButton=(Button)findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

        mModifyButton.setOnClickListener(mModifyListener);
        mNegativeButton.setOnClickListener(mNegativeListener);
    }

    //생성자 생성
    public SelectDialog(Context context, View.OnClickListener modifylistener,
                        View.OnClickListener cancelistener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.mModifyListener=modifylistener;
        this.mNegativeListener = cancelistener;
    }
}