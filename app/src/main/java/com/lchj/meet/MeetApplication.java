package com.lchj.meet;

import android.app.Application;
import android.content.Context;

import com.lchj.meet.bomb.BombManager;

import androidx.multidex.MultiDex;

public class MeetApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = this.getApplicationContext();
        BombManager.getInstance().init(this);
    }
}
