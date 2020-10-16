package com.lchj.meet.event;


import org.greenrobot.eventbus.EventBus;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/16.
 */
public class EventManager {
    public static final int EVENT_TEXT = 1000;

    /**
     * 注册
     *
     * @param subscriber
     */
    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    /**
     * 反注册
     *
     * @param subscriber
     */
    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    /**
     * 启动
     *
     * @param type
     */
    public static void post(int type) {
        EventBus.getDefault().post(new MessageEvent(type));
    }

    /**
     * 启动
     *
     * @param event
     */
    public static void post(MessageEvent event) {
        EventBus.getDefault().post(event);
    }
}
