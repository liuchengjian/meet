package com.lchj.meet.model;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/12.
 */
public class TokenBean {

    /**
     * code : 200
     * userId : 3
     * token : iAOb3Ocl3VOjRBps2DJKs+Gqugvskb15@elt8.cn.rongnav.com;elt8.cn.rongcfg.com
     */

    private int code;
    private String userId;
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
