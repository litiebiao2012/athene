package com.athene.api.common;

/**
 * Created by fe on 16/9/20.
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        if (str == null || str.equals(""))
            return true;
        else
            return false;
    }
}
