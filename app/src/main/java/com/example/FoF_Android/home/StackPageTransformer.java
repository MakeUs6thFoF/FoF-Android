package com.example.FoF_Android.home;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.FoF_Android.R;

public class StackPageTransformer implements ViewPager.PageTransformer {

    private static final float CENTER_PAGE_SCALE = 0.8f;
    private int offscreenPageLimit;
    private ViewPager boundViewPager;

    public StackPageTransformer ( ViewPager boundViewPager) {
        this.boundViewPager = boundViewPager;
        this.offscreenPageLimit = boundViewPager.getOffscreenPageLimit();
    }

    @Override
    public void transformPage( View view, float position) {
        int pagerWidth = boundViewPager.getWidth();
        float horizontalOffsetBase = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + 15;

        if (position >= offscreenPageLimit || position <= -1) {
            //view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }

        if (position >= 0) {
            float translationX = (horizontalOffsetBase - view.getWidth()) * position;
            view.setTranslationX(translationX);
        }
        if (position > -1 && position < 0) {
            float rotation = -position * 30;
            view.setRotation(rotation);
            view.setAlpha((position * position * position + 1));
        } else if (position > offscreenPageLimit - 1) {
         //   view.setAlpha((float) (1 - position + Math.floor(position)));
        } else {
            view.setRotation(0);
         //   view.setAlpha(1);
        }
        if (position == 0) {
       //     view.setScaleX(CENTER_PAGE_SCALE);
        //    view.setScaleY(CENTER_PAGE_SCALE);
        } else {
            float scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE);
          //  view.setScaleX(scaleFactor);
          //  view.setScaleY(scaleFactor);
        }
        ViewCompat.setElevation(view, (offscreenPageLimit - position) * 5);
    }

}
