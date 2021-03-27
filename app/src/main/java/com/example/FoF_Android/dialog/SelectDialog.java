package com.example.FoF_Android.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.FoF_Android.R;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.MemeAllAdapter;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeCase;

import java.util.List;

public class SelectDialog extends Dialog {

    private Button deleteButton;
    private Button mNegativeButton;
    private Button report;
    private View.OnClickListener mPositiveListener;
    private Integer useridx=0;
    private Integer memeuseridx;

    private Integer memeidx;

    TokenManager getuseridx;
    LinearLayout MyModify;
    ReportDialog reportDialog;
    DeleteDialog deleteDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
       // layoutParams.windowAnimations = R.style.AnimationPopupStyle;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_select);
        getuseridx=new TokenManager(getContext());
        useridx=getuseridx.checkIdx(getContext());

        MyModify=(LinearLayout)findViewById(R.id.my_modify);
        report=(Button)findViewById(R.id.report);
        deleteButton=(Button)findViewById(R.id.delete);

        mNegativeButton=(Button)findViewById(R.id.cancel);
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        Log.i("HomeFragmet","useridx="+useridx+"memeuseridx="+memeuseridx);


        if(useridx==memeuseridx){
            MyModify.setVisibility(View.VISIBLE);
            report.setVisibility(View.GONE);
            //셋팅

            //클릭 리스너 셋팅 (클릭버튼이 동작하도록 만들어줌.)

            deleteButton.setOnClickListener(mPositiveListener);


        }else{
            MyModify.setVisibility(View.GONE);
            report.setVisibility(View.VISIBLE);

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    reportDialog= new ReportDialog(getContext(),memeidx);
                    reportDialog.setCancelable(true);
                    reportDialog.getWindow().setGravity(Gravity.CENTER);
                    reportDialog.show();
                }
            });

        }



    }

    //생성자 생성
    public SelectDialog(Context context,Integer memeuseridx ,Integer memeidx, View.OnClickListener mPositiveListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.memeuseridx=memeuseridx;
        this.memeidx=memeidx;
        this.mPositiveListener=mPositiveListener;
    }
}