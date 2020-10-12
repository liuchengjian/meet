package com.lchj.meet.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author by lchj,
 * Email 627107345 @qq.com, Date on 2020/10/12.
 * 哈希计算
 */
public class SHA1 {
    public static String sha1(String data) {
        StringBuffer buffer = new StringBuffer();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes());
            byte[] bits = md.digest();
            for (int i = 0; i < bits.length; i++) {
                int a = bits[i];
                if (a < 0) a += 256;
                if (a < 16) buffer.append("0");
                buffer.append((Integer.toHexString(a)));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
