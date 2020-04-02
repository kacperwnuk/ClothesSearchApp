package com.example.clothessearchapp.materialux;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

/**
 * for more visit http://materialuiux.com
 */
public class ViewPagerNoSwipe extends ViewPager {
    /**
     * Is swipe enabled
     */
    private boolean enabled;

    public ViewPagerNoSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false; // By default swiping is disabled
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.enabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.enabled && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean executeKeyEvent(KeyEvent event) {
        return this.enabled && super.executeKeyEvent(event);
    }

    public void setSwipeEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}