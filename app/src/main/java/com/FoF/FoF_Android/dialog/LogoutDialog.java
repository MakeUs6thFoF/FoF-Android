package com.FoF.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.FoF.FoF_Android.R;
import com.FoF.FoF_Android.TokenManager;

public class LogoutDialog extends Dialog {

    private Button mPositiveButton;
    private Button mNegativeButton;

    private View.OnClickListener mPositiveListener=null;
    private View.OnClickListener mNegativeListener;
    Integer type=0;

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


        TextView textView2 = findViewById(R.id.textView2);
        TextView textView5 = findViewById(R.id.textView5);
        mPositiveButton = (Button) findViewById(R.id.delete);

        if(type==0) {

            textView2.setText("잠시만 안녕");

            textView5.setText("해당계정을\n로그아웃하겠습니까?");

            mPositiveButton.setText("확인");
        }

        else if(type==1){

            textView5.setText("현재 편집을\n삭제하겠습니까?");
        }else if(type==2) {

            textView5.setText("프로필 사진을\n삭제하겠습니까?");

            mPositiveButton.setText("확인");
        }else if(type==3){
            textView2.setText("");

            textView5.setText("해당 사진으로\n수정하겠습니까?");

            mPositiveButton.setText("확인");
        }
        //셋팅

        mNegativeButton=(Button)findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
     /*   mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(api);
                dismiss();
                Toast.makeText(getContext(), "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });*/
        mPositiveButton.setOnClickListener(mNegativeListener);
      if(mPositiveListener==null) {  mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }else mNegativeButton.setOnClickListener(mPositiveListener);
    }


    //생성자 생성

    public LogoutDialog(Integer type, Context context, View.OnClickListener mNegativeListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.type=type;
        this.mNegativeListener=mNegativeListener;
    }

    public LogoutDialog(Integer type, Context context, View.OnClickListener mNegativeListener, View.OnClickListener mPositiveListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.type=type;
        this.mNegativeListener=mNegativeListener;
        this.mPositiveListener=mPositiveListener;
    }


}

