package com.lchj.meet;

import android.app.Application;
import android.content.Context;

import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.utils.LiuUtils;

import androidx.multidex.MultiDex;
import io.rong.imlib.RongIMClient;

public class MeetApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = this.getApplicationContext();
        BombManager.getInstance().init(this);
        String appKey = "mgb7ka1nmen5g";
        RongIMClient.init(this, appKey);
        RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            /**
             * 连接状态返回回调
             * @param status 状态值
             */
            @Override
            public void onChanged(ConnectionStatus status) {
                switch (status) {
                    case CONN_USER_BLOCKED:
                        //用户被开发者后台封禁
                        break;
                    case CONNECTED:
                        // 连接成功。
                        LiuUtils.makeText(mContext,"融云--连接成功");
                        break;
                    case CONNECTING:
                        // 连接中。
                        break;
                    case DISCONNECTED:
                        LiuUtils.makeText(mContext,"融云--断开连接");
                        // 断开连接。。
                        break;
                    case KICKED_OFFLINE_BY_OTHER_CLIENT:
                        // 用户账户在其他设备登录，本机会被踢掉线。
                        break;
                    case NETWORK_UNAVAILABLE:
                        //  网络不可用。
                        break;
                    case SERVER_INVALID:
                        // 服务器异常或无法连接。
                        break;
                    case TOKEN_INCORRECT:
                        // Token 不正确。
                        LiuUtils.makeText(mContext,"融云--oken 不正确");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
