package com.lchj.meet.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int layoutResID = initView(savedInstanceState);
        //如果 initView 返回 0, 框架则不会调用 setContentView(), 当然也不会 Bind ButterKnife
        if (layoutResID != 0) {
            setContentView(layoutResID);
            //绑定到butterknife
            mUnbinder = ButterKnife.bind(this);
        }

    }


    /**
     * 初始化 View, 如果 {@link #initView(Bundle)} 返回 0,
     *
     * @param savedInstanceState
     * @return
     */
    abstract int initView(@Nullable Bundle savedInstanceState);

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) mUnbinder.unbind();
        this.mUnbinder = null;
    }

}
