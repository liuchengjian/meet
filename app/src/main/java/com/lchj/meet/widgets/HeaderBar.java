package com.lchj.meet.widgets;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lchj.meet.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class HeaderBar extends FrameLayout {

    //是否显示"返回"图标
    private boolean isShowBack = true;
    //Title文字
    private String titleText = null;
    //右侧文字
    private String rightText = null;
    private ImageView mLeftIv;
    private TextView mTitleTv;
    private TextView mRightTv;

    public HeaderBar(@NonNull Context context) {
        super(context);
        init(context, null);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public HeaderBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs) {
        //获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderBar);

        isShowBack = typedArray.getBoolean(R.styleable.HeaderBar_isShowBack, true);

        titleText = typedArray.getString(R.styleable.HeaderBar_titleText);
        rightText = typedArray.getString(R.styleable.HeaderBar_rightText);

        initView(context);
        typedArray.recycle();
    }

    private void initView(Context context) {
        View view = View.inflate(context, R.layout.layout_header_bar, this);
        mLeftIv = view.findViewById(R.id.mLeftIv);
        mTitleTv = view.findViewById(R.id.mTitleTv);
        mRightTv = view.findViewById(R.id.mRightTv);
        mLeftIv.setVisibility(isShowBack ? View.VISIBLE : View.GONE);
        //标题不为空，设置值
        mTitleTv.setText(titleText);
        mRightTv.setText(rightText);
        //返回图标默认实现（关闭Activity）
        mLeftIv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (context instanceof Activity) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    /**
     * 获取左侧视图
     *
     * @return
     */
    public ImageView getLeftView() {
        return mLeftIv;
    }

    /**
     * 获取右侧视图
     *
     * @return
     */
    public TextView getRightView() {
        return mRightTv;
    }

    /**
     * 获取右侧文字
     *
     * @return
     */
    public String getRightText() {
        return mRightTv.getText().toString();
    }
}
