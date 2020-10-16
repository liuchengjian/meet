package com.lchj.meet.model;

import cn.bmob.v3.BmobObject;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/15.
 */
public class Friend extends BmobObject {
    private User user;
    private User yourUser;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getYourUser() {
        return yourUser;
    }

    public void setYourUser(User yourUser) {
        this.yourUser = yourUser;
    }
}
