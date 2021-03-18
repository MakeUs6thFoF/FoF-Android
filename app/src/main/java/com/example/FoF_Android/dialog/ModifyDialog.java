package com.example.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.FoF_Android.R;

public class ModifyDialog extends Dialog {
    private Button mPositiveButton;
    private ImageButton mNegativeButton;

    private View.OnClickListener mPositiveListener;
    private View.OnClickListener mNegativeListener;
    private  TextView copyright;
    String copy;
    Integer memeIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.dialog_copyright);

        //셋팅
        mPositiveButton=(Button)findViewById(R.id.modify);
        mNegativeButton=(ImageButton)findViewById(R.id.cancel);

        //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)
        mNegativeButton.setOnClickListener(mNegativeListener);
        mPositiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isShowing()) {
                    Intent modifyintent=new Intent(getContext(), ModifyCopyrightActivity.class);

                    modifyintent.putExtra("memeIdx",memeIdx);
                    getContext().startActivity(modifyintent);
                    // Intent intent = new Intent(    , Register.class);


                    dismiss();}
            }
        });

        copyright=(TextView)findViewById(R.id.copyright);
        copyright.setText(copy);
    }

    //생성자 생성
    public ModifyDialog(Context context, String copy, Integer memeIdx,
                        View.OnClickListener cancelistener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mNegativeListener = cancelistener;
        this.memeIdx=memeIdx;
        this.copy=copy;
    }
}
