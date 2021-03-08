package com.example.FoF_Android.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MagnifyingAnimPageTransInterface implements ViewPager.PageTransformer{

    private static int BIAS = 200;
    private boolean isMovePrev = false;
    private float currentOffset;
    private float prevOffset;
    public int baseItemViewHeight = 0;
    public int tempItemViewHeight = 0;

    public MagnifyingAnimPageTransInterface(int bias, int viewPagerHeight){
        if(bias > 0){
            BIAS = bias;
        }

        baseItemViewHeight = viewPagerHeight;
        tempItemViewHeight = baseItemViewHeight - bias;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        currentOffset = position;
        ImageView imageView = null;

        if(page instanceof LinearLayout){
            LinearLayout targetLayout = (LinearLayout) page;

            if(targetLayout.getChildCount() > 0)
                imageView = (ImageView)targetLayout.getChildAt(0);
        }
        else
            return;

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();

        if((int)imageView.getTag() == 0){
            if(currentOffset < prevOffset)
                isMovePrev = true;
            else
                isMovePrev = false;
        }

        if (position < -1)
        { /* 왼쪽으로 더이상 못가게*/ }
        else if(position <= 0){
            if(baseItemViewHeight - Math.round(BIAS*Math.abs(position)) > baseItemViewHeight - BIAS){
                params.height = baseItemViewHeight - Math.round(BIAS * Math.abs(position));
            }
        }
        else if(position <= 1){
            int curHeight = params.height;

            if(isMovePrev){
                if(baseItemViewHeight > curHeight)
                    params.height = tempItemViewHeight + Math.round(BIAS * (1 - Math.abs(position)));
            }
            else
                if(baseItemViewHeight > curHeight)
                    params.height = tempItemViewHeight + Math.round(BIAS * (1 - Math.abs(position)));
        }

        else {
            if (baseItemViewHeight - Math.round(BIAS * (Math.abs(position) - 1)) > baseItemViewHeight - BIAS) {
                params.height = baseItemViewHeight - Math.round(BIAS * Math.abs(position));
            }
        }

        prevOffset = currentOffset;
        imageView.setLayoutParams(params);
    }
}
