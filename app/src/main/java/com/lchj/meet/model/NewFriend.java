package com.lchj.meet.model;

import org.litepal.crud.LitePalSupport;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/16.
 */
public class NewFriend extends LitePalSupport {
    private String msg;
    private String yourUserId;
    private Long saveTime;
    private int isAgree=-1;//-1待确定，0，同意，1，拒绝

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getYourUserId() {
        return yourUserId;
    }

    public void setYourUserId(String yourUserId) {
        this.yourUserId = yourUserId;
    }

    public Long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Long saveTime) {
        this.saveTime = saveTime;
    }

    public int getIsAgree() {
        return isAgree;
    }

    public void setIsAgree(int isAgree) {
        this.isAgree = isAgree;
    }
}
