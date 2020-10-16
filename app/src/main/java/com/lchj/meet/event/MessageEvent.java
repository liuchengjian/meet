package com.lchj.meet.event;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/16.
 */
public class MessageEvent {
    private int type;

    public MessageEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
