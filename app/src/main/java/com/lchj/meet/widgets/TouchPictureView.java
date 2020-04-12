package com.lchj.meet.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.lchj.meet.R;

import androidx.annotation.Nullable;

/**
 * 拖动图片验证
 */
public class TouchPictureView extends View {
    private Bitmap bgBitmap;
    private Paint mPointbg;
    //空白快
    private Bitmap mNullBitmap;
    private Paint mNullPoint;
    //移动快
    private Bitmap mMoveBitmap;
    private Paint mMovePoint;
    //View的宽高
    private int mWidth;
    private int mHight;
    private int CRAD_SIZE = 200;
    private int LINE_W, LINE_H = 0;
    //移动方块显示的位置
    private int moveX = 200;
    //误差值
    private int errorValues = 10;
    private OnViewResultListener onViewResultListener;

    //外部调用的接口
    public void setOnViewResultListener(OnViewResultListener onViewResultListener) {
        this.onViewResultListener = onViewResultListener;
    }

    public TouchPictureView(Context context) {
        super(context);
        init();
    }

    public TouchPictureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchPictureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init() {
        mPointbg = new Paint();
        mNullPoint = new Paint();
        mMovePoint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHight = h;
    }

    //滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //点击方块才可以拖动

                break;
            case MotionEvent.ACTION_MOVE:
                //防止越界
                if (event.getX() > 0 && event.getX() < (mWidth - CRAD_SIZE)) {
                    moveX = (int) event.getX();
                    if (moveX > (LINE_W - errorValues) && moveX < (LINE_W + errorValues)) {
                        if (onViewResultListener != null) {
                            onViewResultListener.onResult();
                            moveX = 200;
                        }
                    }
                    //刷新
                    invalidate();
                }

                break;
        }
        return true;
    }

    /**
     * onDraw 绘制
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawNullCard(canvas);
        drawMoveCard(canvas);
    }

    /**
     * 绘制移动的方块
     *
     * @param canvas
     */
    private void drawMoveCard(Canvas canvas) {
        //截取空白块位置图片作为背景
        mMoveBitmap = Bitmap.createBitmap(bgBitmap, LINE_W, LINE_H, CRAD_SIZE, CRAD_SIZE);
        //绘制到View上
        canvas.drawBitmap(mMoveBitmap, moveX, LINE_H, mMovePoint);

    }

    /**
     * 绘制白块
     */
    private void drawNullCard(Canvas canvas) {
        mNullBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_null_view);
        //计算值
        CRAD_SIZE = mNullBitmap.getWidth();
        LINE_W = mWidth / 3 * 2;
        LINE_H = mHight / 2 - (CRAD_SIZE / 2);
        //绘制
        canvas.drawBitmap(mNullBitmap, LINE_W, LINE_H, mNullPoint);
    }

    /**
     * 绘制背景
     */
    private void drawBg(Canvas canvas) {
        //1获取背景
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_bg);
        //创建空的Bitmap
        bgBitmap = Bitmap.createBitmap(mWidth, mHight, Bitmap.Config.ARGB_8888);
        //将图片绘制到空的Bitmap
        Canvas bgCanvas = new Canvas(bgBitmap);
        Rect mDestRect = new Rect(0, 0, mWidth, mHight);
        bgCanvas.drawBitmap(mBitmap, null, mDestRect, mPointbg);
        //将bgBitmap绘制到View上
        canvas.drawBitmap(bgBitmap, null, mDestRect, mPointbg);

    }

    public interface OnViewResultListener {
        void onResult();
    }

}
