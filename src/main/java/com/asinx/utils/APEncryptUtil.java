package com.asinx.utils;

import cn.hutool.core.codec.Base64;
import com.google.common.base.Strings;

/**
 * encrypt util
 */
public class APEncryptUtil {

    /**
     * encode
     *
     * @param secret
     * @param content
     * @return
     */
    public static String encode(String secret, String content) {
        String base64String = Base64.encode(content);
        System.out.println("base64String=" + base64String);
        String aesString = AesAPIUtils.encode(content, secret);
        System.out.println("aesString=" + aesString);
        return MD5Util.digest(aesString);
    }

    /**
     * decode
     *
     * @param secret
     * @param content
     * @return
     */
    public static String decode(String secret, String content) {
        if (Strings.isNullOrEmpty(content)) {
            return null;
        }
        String base64String = AesAPIUtils.decode(content, secret);
        return base64String;
    }
}
