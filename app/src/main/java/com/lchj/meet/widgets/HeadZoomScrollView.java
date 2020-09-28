package com.lchj.meet.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.blankj.utilcode.util.LogUtils;

import java.util.logging.Logger;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/9/28.
 */
public class HeadZoomScrollView extends ScrollView {
    private View mZoomView;
    private int mZoomViewWidth;
    private int mZoomViewHeight;
    private boolean isScrolling = false;//是否滑动
    private float firstPosition;//第一次按下坐标
    private float mScrollRate = 0.5f;//滑动系数
    private float mReplyRate = 0.7f;//回弹系数


    public HeadZoomScrollView(Context context) {
        super(context);
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HeadZoomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 1.获取头部的View
     * 2.滑动事件的处理
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildAt(0) != null) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(0);
            if (viewGroup.getChildAt(0) != null) {
                mZoomView = viewGroup.getChildAt(0);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            mZoomViewWidth = mZoomView.getMeasuredWidth();
            mZoomViewHeight = mZoomView.getMeasuredHeight();
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!isScrolling) {
                    //说明没有滑动
                    if (getScrollY() == 0) {
                        //第一次滑动
                        firstPosition = ev.getY();
                    } else {
                        break;
                    }
                }
                //计算缩放值 （当前位置-第一次按下的位置）*缩放系数
                int distance = (int) ((ev.getY() - firstPosition) * mScrollRate);
                if (distance < 0) {
                    break;
                }
                LogUtils.d(distance);
                isScrolling = true;
                setZoomView(distance);
                break;
            case MotionEvent.ACTION_UP:
                replyZoomView();
                isScrolling= false;
                break;
        }

        return true;
    }

    /**
     * 回弹的动画
     */
    private void replyZoomView() {
        //计划下拉的缩放值让属性动画根据这个复原
        int distance = mZoomView.getMeasuredWidth() - mZoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0)
                .setDuration((long) (distance * mReplyRate));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoomView((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();

    }

    /**
     * 缩放View
     *
     * @param zoom
     */
    private void setZoomView(float zoom) {
        if (mZoomViewWidth <= 0 || mZoomViewHeight <= 0) {
            return;
        }
        ViewGroup.LayoutParams lp = mZoomView.getLayoutParams();
        lp.width = (int) (mZoomViewWidth + zoom);
        lp.height = (int) (mZoomViewHeight * ((mZoomViewWidth + zoom) / mZoomViewWidth));
        //设置间距
        //（-（lp.width - 原本的宽）/2）
        ((MarginLayoutParams) lp).setMargins(-(lp.width - mZoomViewWidth) / 2, 0, 0, 0);
        mZoomView.setLayoutParams(lp);
    }
}
