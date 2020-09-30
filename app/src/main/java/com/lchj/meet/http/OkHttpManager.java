package com.lchj.meet.http;

import com.lchj.meet.bomb.BombManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/9/30.
 */
public class OkHttpManager {
    private volatile static OkHttpManager mInstance = null;
    private OkHttpClient mOkHttpClient;
    private static final String TOKEN_URL = " http://api-cn.ronghub.com/user/getToken.json";

    public OkHttpManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpManager getInstance() {
        if (mInstance == null) {
            synchronized (BombManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 请求融云token
     *
     * @param map
     */
    public String postCloudToken(HashMap<String, String> map){
        String Timestamp = String.valueOf(System.currentTimeMillis() / 1000);
        String AppKey = "A1qqLb0mAU9";
        String Nonce = String.valueOf(Math.floor(Math.random() * 10000));
        String Signature = "";
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key));
        }
        RequestBody requestBody = builder.build();
        //添加签名规则
        final Request request = new Request.Builder()
                .url(TOKEN_URL)
                .addHeader("Timestamp", Timestamp)
                .addHeader("App-Key", AppKey)
                .addHeader("Nonce", Nonce)
                .addHeader("Signature", Signature)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        try {
            return mOkHttpClient.newCall(request).execute().body().string();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
