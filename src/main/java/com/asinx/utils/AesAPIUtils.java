package com.asinx.utils;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class AesAPIUtils {

    public static String encode(String data, String apiKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, HexUtil.decodeHex(apiKey));
        data = Base64.encode(data);
        return aes.encryptHex(data,CharsetUtil.CHARSET_UTF_8);
    }

    public static String decode(String encodeData, String apiKey) {
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, HexUtil.decodeHex(apiKey));
        return Base64.decodeStr(aes.decryptStr(encodeData,CharsetUtil.CHARSET_UTF_8));
    }

    public static void main(String[] arg) {

        String aes = encode("123456","b635dd5c87f7bf73387929203321b1e1");
        System.out.println(aes);
        System.out.println(decode(aes,"b635dd5c87f7bf73387929203321b1e1"));
    }
}
