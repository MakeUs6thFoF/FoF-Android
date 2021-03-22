package com.example.FoF_Android.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.viewpager.widget.ViewPager;

public class CustomSwipeableViewPager extends ViewPager {


    public CustomSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new XScrollDetector());

    }

    // -----------------------------------------------------------------------
    //
    // Fields
    //
    // -----------------------------------------------------------------------
    private GestureDetector mGestureDetector;
    private boolean isLockOnHorizontalAxis = false;

    // -----------------------------------------------------------------------
    //
    // Methods
    //
    // -----------------------------------------------------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // decide if horizontal axis is locked already or we need to check the scrolling direction
        if (!isLockOnHorizontalAxis)
            isLockOnHorizontalAxis = mGestureDetector.onTouchEvent(event);

        // release the lock when finger is up
        if (event.getAction() == MotionEvent.ACTION_UP)
            isLockOnHorizontalAxis = false;

        getParent().requestDisallowInterceptTouchEvent(isLockOnHorizontalAxis);
        return super.onTouchEvent(event);
    }

    // -----------------------------------------------------------------------
    //
    // Inner Classes
    //
    // -----------------------------------------------------------------------
    private class XScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceX) > Math.abs(distanceY);
        }

    }
    View.OnTouchListener viewpagerTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (!isLockOnHorizontalAxis)
                isLockOnHorizontalAxis = mGestureDetector.onTouchEvent(event);

            if (event.getAction() == MotionEvent.ACTION_UP)
                isLockOnHorizontalAxis = false;

            if (isLockOnHorizontalAxis) {

            } else if (!isLockOnHorizontalAxis) {

            }
            return false;
        }
    };

}
