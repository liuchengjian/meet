package com.lchj.meet;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.cloud.CloudManager;
import com.lchj.meet.utils.LiuUtils;

import org.litepal.LitePal;

import androidx.multidex.MultiDex;
import io.rong.imlib.RongIMClient;

public class MeetApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = this.getApplicationContext();
        if(getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))){
            BombManager.getInstance().init(this);
            CloudManager.getInstance().init(this);
            LitePal.initialize(this);
        }
    }
    /**
     * 获取当前进程名
     *
     * @param context
     * @return
     */
    public static String getCurProcessName(Context context) {
        int pid = Process.myPid();
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : manager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
