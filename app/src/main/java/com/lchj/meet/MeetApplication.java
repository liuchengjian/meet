package com.lchj.meet;

import android.app.Application;
import android.content.Context;

import com.lchj.meet.bomb.BombManager;

public class MeetApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext =this.getApplicationContext();
        BombManager.getInstance().init(this);
    }
}
