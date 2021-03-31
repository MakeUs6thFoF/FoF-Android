package com.FoF.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.RetrofitApi;
import com.FoF.FoF_Android.TokenManager;

public class DeleteDialog extends Dialog {

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
      /*  mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletememe(api);
                dismiss();
                Toast.makeText(getContext(), "삭제 하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });*/
        mPositiveButton.setOnClickListener(mNegativeListener);
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    //생성자 생성
    public DeleteDialog(Context context, Integer memeidx,     View.OnClickListener mNegativeListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.memeidx=memeidx;
        this.mNegativeListener=mNegativeListener;
    }

}

