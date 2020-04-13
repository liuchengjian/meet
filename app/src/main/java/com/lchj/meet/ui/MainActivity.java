package com.lchj.meet.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lchj.meet.R;
import com.lchj.meet.model.User;
import com.lchj.meet.utils.LiuUtils;
import com.lchj.meet.widgets.TouchPictureView;

public class MainActivity extends BaseActivity {
    private TouchPictureView mTouchPictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTouchPictureView = findViewById(R.id.mTouchPictureView);
        mTouchPictureView.setOnViewResultListener(() -> LiuUtils.makeText(MainActivity.this, "验证成功"));
    }

    @Override
    int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }


}
