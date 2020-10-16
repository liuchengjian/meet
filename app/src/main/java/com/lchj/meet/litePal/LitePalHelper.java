package com.lchj.meet.litePal;

import com.lchj.meet.bomb.BombManager;
import com.lchj.meet.model.NewFriend;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.List;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/16.
 * 本地数据库帮助类
 */
public class LitePalHelper {
    private volatile static LitePalHelper mInstance = null;

    public LitePalHelper() {
    }

    public static LitePalHelper getInstance() {
        if (mInstance == null) {
            synchronized (LitePalHelper.class) {
                if (mInstance == null) {
                    mInstance = new LitePalHelper();
                }
            }
        }
        return mInstance;
    }

    private void baseSave(LitePalSupport support) {
        support.save();
    }

    /**
     * 保存新朋友
     *
     * @param msg
     * @param userId
     */
    public void saveNewFriend(String msg, String userId) {
        NewFriend newFriend = new NewFriend();
        newFriend.setMsg(msg);
        newFriend.setYourUserId(userId);
        newFriend.setIsAgree(-1);
        newFriend.setSaveTime(System.currentTimeMillis());
        baseSave(newFriend);
    }

    private List<? extends LitePalSupport> baseQuery(Class cls) {
        return LitePal.findAll(cls);
    }

    /**
     * 查询新朋友
     *
     * @return
     */
    public List<NewFriend> queryNewFriend() {
        return (List<NewFriend>) baseQuery(NewFriend.class);
    }

    /**
     * 更新新朋友数据库的状态
     * @param userId
     * @param agree
     */
    public void updateNewFriend(String userId, int agree) {
        NewFriend newFriend = new NewFriend();
        newFriend.setIsAgree(agree);
        newFriend.updateAll("yourUserId = ?", userId);
    }
}
