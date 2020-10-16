package com.lchj.meet.ui.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.lchj.meet.cloud.CloudManager;
import com.lchj.meet.common.Const;
import com.lchj.meet.litePal.LitePalHelper;
import com.lchj.meet.model.NewFriend;
import com.lchj.meet.model.TextBean;

import org.litepal.LitePal;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/12.
 */
public class CloudService extends Service {
    private Disposable disposable;

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
                Log.e("message", "收到消息:" + message);
                String objectName = message.getObjectName();
                if (objectName.equals(CloudManager.MSG_TEXT_NAME)) {
                    //文本消息
                    TextMessage textMessage = (TextMessage) message.getContent();
                    String content = textMessage.getContent();
                    if (!TextUtils.isEmpty(content)) {
                        TextBean textBean = GsonUtils.fromJson(content, TextBean.class);
                        if (textBean.getType().equals(CloudManager.TYPE_TEXT)) {
                            //聊天消息
                        } else if (textBean.getType().equals(CloudManager.TYPE_ADD_FRIEND)) {
                            //添加好友
                            disposable = Observable.create(new ObservableOnSubscribe<List<NewFriend>>() {
                                @Override
                                public void subscribe(ObservableEmitter<List<NewFriend>> emitter) throws Exception {
                                    emitter.onNext(LitePalHelper.getInstance().queryNewFriend());
                                    emitter.onComplete();
                                }
                            }).subscribeOn(Schedulers.newThread())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new Consumer<List<NewFriend>>() {
                                        @Override
                                        public void accept(List<NewFriend> newFriends) throws Exception {
                                            //更新UI
                                            if (!newFriends.isEmpty()) {
                                                boolean isHave = false;
                                                for (int j = 0; j < newFriends.size(); j++) {
                                                    if (message.getSenderUserId().equals(newFriends.get(i).getYourUserId())) {
                                                        isHave = true;
                                                        break;
                                                    }
                                                }
                                                if (!isHave) {
                                                    LitePalHelper.getInstance().saveNewFriend(textBean.getMsg(),
                                                            message.getSenderUserId());
                                                }
                                            }else {
                                                LitePalHelper.getInstance().saveNewFriend(textBean.getMsg(),
                                                        message.getSenderUserId());
                                            }
                                        }
                                    });
                        } else if (textBean.getType().equals(CloudManager.TYPE_AGREE_FRIEND)) {
                            //同意好友
                        }
                    }
                } else {

                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposable != null && disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
