package com.lchj.meet.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lchj.meet.R;

public class ChatActivity extends BaseActivity {

    public static void startActivity(Context mContext,String yourUserId,String yourUrl){
        Intent intent = new Intent(mContext,ChatActivity.class);
        intent.putExtra("userId",yourUserId);
        intent.putExtra("url",yourUrl);
        mContext.startActivity(intent);
    }
    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_chat;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }
}