package com.lchj.meet.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.lchj.meet.cloud.CloudManager;
import com.lchj.meet.common.Const;

import androidx.annotation.Nullable;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/12.
 */
public class CloudService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        linkCloudService();
    }

    /**
     * 连接云服务
     */
    private void linkCloudService() {
        String token = SPUtils.getInstance().getString(Const.CLOUD_TOKEN, "");
        Log.e("token", "token:" + token);
        CloudManager.getInstance().connect(token);
        CloudManager.getInstance().setOnReceiverMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                Log.e("message", "message:" + message);
                return false;
            }
        });
    }
}
