package com.lchj.meet.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lchj.meet.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }


}
