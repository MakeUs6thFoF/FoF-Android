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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.FoF_Android.R;
import com.example.FoF_Android.TokenManager;
import com.example.FoF_Android.home.MemeAllAdapter;
import com.example.FoF_Android.home.model.Meme;
import com.example.FoF_Android.home.model.MemeCase;

import java.util.List;

public class ProfileImgDialog extends Dialog {

    private Button deleteButton,modifyButtom;
    private Button mNegativeButton;
    private Button report;
    private View.OnClickListener mdeletelistener;
    private View.OnClickListener mselectlistener;
    TokenManager getuseridx;


    public ProfileImgDialog(Context context, View.OnClickListener mdeletelistener, View.OnClickListener mselectlistener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mdeletelistener=mdeletelistener;
        this.mselectlistener=mselectlistener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        // layoutParams.windowAnimations = R.style.AnimationPopupStyle;
        getWindow().setAttributes(layoutParams);
        setContentView(R.layout.dialog_profileimg);


        deleteButton=(Button)findViewById(R.id.delete);
        modifyButtom=(Button)findViewById(R.id.select);

        mNegativeButton=(Button)findViewById(R.id.cancel);
        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        deleteButton.setOnClickListener(mdeletelistener);
        modifyButtom.setOnClickListener(mselectlistener);

    }
}