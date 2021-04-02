package com.FoF.FoF_Android;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

public class OnboardingActivity extends Dialog {
    private ImageButton mNegativeButton;
    public OnboardingActivity(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //다이얼로그 밖의 화면은 흐리게 만들어줌
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;

        getWindow().setAttributes(layoutParams);

        setContentView(R.layout.activity_onboarding);
        mNegativeButton=(ImageButton)findViewById(R.id.cancel);


        mNegativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor sharedPreferencesEditor =
                        PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
                sharedPreferencesEditor.putBoolean(
                        "onBoarding", true);
                sharedPreferencesEditor.apply();
                dismiss();
            }
        });
    }


}
