package com.lchj.meet.bomb;

import android.content.Context;

import cn.bmob.v3.Bmob;

public class BombManager {
    private final static String BOMB_SDK_KEY = "d9314358713765e621406c5bc71a0c26";
    private volatile static BombManager mInstance = null;

    public BombManager() {
    }

    public static BombManager getInstance() {
        if (mInstance == null) {
            synchronized (BombManager.class) {
                if (mInstance == null) {
                    mInstance = new BombManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 默认初始化
     * @param context
     */
    public void init(Context context){
        //第一：
        Bmob.initialize(context, BOMB_SDK_KEY);
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        //BmobConfig config =new BmobConfig.Builder(this)
        ////设置appkey
        //.setApplicationId("Your Application ID")
        ////请求超时时间（单位为秒）：默认15s
        //.setConnectTimeout(30)
        ////文件分片上传时每片的大小（单位字节），默认512*1024
        //.setUploadBlockSize(1024*1024)
        ////文件的过期时间(单位为秒)：默认1800s
        //.setFileExpiration(2500)
        //.build();
        //Bmob.initialize(config);
    }
}
