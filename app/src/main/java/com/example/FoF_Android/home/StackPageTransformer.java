package com.example.FoF_Android.home;

import android.annotation.SuppressLint;
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

    private static final float CENTER_PAGE_SCALE = 1f;
    private int offscreenPageLimit;
    private ViewPager boundViewPager;

    public StackPageTransformer ( ViewPager boundViewPager) {
        this.boundViewPager = boundViewPager;
        this.offscreenPageLimit = boundViewPager.getOffscreenPageLimit();
    }


    @Override
    public void transformPage( View view, float position) {
        View leftOverlay = view.findViewById(R.id.left_overlay);
        View rightOverlay = view.findViewById(R.id.right_overlay);
        int pagerWidth = boundViewPager.getWidth();
        int pagerHeight = boundViewPager.getHeight();//
        float horizontalOffsetBase = (pagerWidth - pagerWidth * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + 10;
        float verticalOffsetBase = (pagerHeight - pagerHeight * CENTER_PAGE_SCALE) / 2 / offscreenPageLimit + 8;//
        leftOverlay.setAlpha(0);
        if (position >= offscreenPageLimit || position <= -1) {
            view.setAlpha(position * position * position + 1);//
            view.setVisibility(View.GONE);
            rightOverlay.setAlpha(1);
            leftOverlay.setAlpha(0);
        } else {
            view.setVisibility(View.VISIBLE);

            leftOverlay.setAlpha(0);
        }

        if (position >= 0) {
            float translationX = (horizontalOffsetBase - view.getWidth()) * position;
            float translationY = (verticalOffsetBase) * position;
            view.setTranslationX(translationX);
            view.setTranslationY(translationY);
          leftOverlay.setAlpha(0);
          rightOverlay.setAlpha(0);
        }
        if (position > -1 && position < 0) {
            leftOverlay.setAlpha(1);
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
            view.setScaleX(CENTER_PAGE_SCALE);
            view.setScaleY(CENTER_PAGE_SCALE);
        } else {
            float scaleFactor = Math.min(CENTER_PAGE_SCALE - position * 0.1f, CENTER_PAGE_SCALE);
          //  view.setScaleX(scaleFactor);
         //   view.setScaleY(scaleFactor);

        }
        ViewCompat.setElevation(view, (offscreenPageLimit - position) );
    }

}
