package com.dingup.onlinetest.util;

import java.util.Random;

/**
 * Created by yanggavin on 16/3/27.
 */
public class StrUtil {
    public static String getRandomStr(int length) {
        StringBuffer buffer = new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i ++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }
}
