package com.lchj.meet;

import android.app.Application;

import com.lchj.meet.bomb.BombManager;

public class MeetApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BombManager.getInstance().init(this);
    }
}
