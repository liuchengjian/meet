package com.lchj.meet.cloud;

import android.content.Context;
import android.util.Log;

import com.lchj.meet.bomb.BombManager;

import org.json.JSONObject;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/12.
 */
public class CloudManager {
    private volatile static CloudManager mInstance = null;
    public static final String TOKEN_URL = "http://api-cn.ronghub.com/user/getToken.json";
    public static final String CLOUD_KEY = "mgb7ka1nmen5g";
    public static final String CLOUD_SECRET = "A1qqLb0mAU9";
    //ObjectName
    public static final String MSG_TEXT_NAME = "RC:TxtMsg";//文本类型
    public static final String MSG_IMG_NAME = "RC:ImgMsg";//图片类型
    public static final String MSG_LOCATION_NAME = "RC:LBSMsg";//位置类型
    //msg
    public static final String TYPE_TEXT = "TYPE_TEXT";//普通聊天消息
    public static final String TYPE_ADD_FRIEND = "TYPE_ADD_FRIEND";//添加好友
    public static final String TYPE_AGREE_FRIEND = "TYPE_AGREE_FRIEND";//同意好友

    public CloudManager() {
    }

    public static CloudManager getInstance() {
        if (mInstance == null) {
            synchronized (CloudManager.class) {
                if (mInstance == null) {
                    mInstance = new CloudManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化融云SDK
     *
     * @param context
     */
    public void init(Context context) {
        RongIMClient.init(context, CLOUD_KEY);
    }

    /**
     * 连接融云服务
     *
     * @param token
     */
    public void connect(String token) {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                Log.e("connect！", "连接成功" + s);
//                CloudManager.getInstance().sendTextMessage("1111111", "9ccff03bb9");
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {
                Log.e("connect！", "连接失败" + connectionErrorCode);
                if (connectionErrorCode.equals(RongIMClient.ConnectionErrorCode.RC_CONN_TOKEN_INCORRECT)) {
                    //从 APP 服务获取新 token，并重连
                } else {
                    //无法连接 IM 服务器，请根据相应的错误码作出对应处理
                }
            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                //消息数据库打开，可以进入到主页面
            }
        });
    }

    /**
     * 断开连接
     */
    public void disconnect() {
        RongIMClient.getInstance().disconnect();
    }

    /**
     * 退出登录
     */
    public void logout() {
        RongIMClient.getInstance().logout();
    }

    /**
     * 接收消息的监听器
     *
     * @param listener
     */
    public void setOnReceiverMessageListener(RongIMClient.OnReceiveMessageListener listener) {
        RongIMClient.setOnReceiveMessageListener(listener);
    }

    /**
     * 发送消息的回调
     */
    private IRongCallback.ISendMessageCallback iSendMessageCallback = new IRongCallback.ISendMessageCallback() {
        /**
         * 消息发送前回调, 回调时消息已存储数据库
         * @param message 已存库的消息体
         */
        @Override
        public void onAttached(Message message) {

        }

        @Override
        public void onSuccess(Message message) {
            Log.e("IRong", "onSuccess:" + message);
        }

        @Override
        public void onError(Message message, RongIMClient.ErrorCode errorCode) {
            Log.e("IRong", "onError:" + errorCode);
        }
    };

    /**
     * 发送文本消息
     *
     * @param content
     * @param targetId
     */
    public void sendTextMessage(String content, String targetId) {
        TextMessage textMsg = TextMessage.obtain(content);
        RongIMClient.getInstance().sendMessage(
                Conversation.ConversationType.PRIVATE,
                targetId,
                textMsg,
                null,
                null,
                iSendMessageCallback);
    }

    /**
     * 发送消息
     *
     * @param msg
     * @param type 为空，则为普通消息，不为空为聊天文本消息
     * @param targetId
     */
    public void sendTextMessage(String msg, String type, String targetId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msg", msg);
            jsonObject.put("type", type);
            sendTextMessage(jsonObject.toString(), targetId);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
