
package com.asinx.utils;

import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法
 * 
 *
 */
public class MD5Util {
    private static final String ALGORITHM = "MD5";
    
    public static String digest(String in) {
        try {
            return DatatypeConverter.printHexBinary(digest(in.getBytes("UTF-8"))).toLowerCase();
        } catch (UnsupportedEncodingException e) {
            // can't be here
            throw new RuntimeException(e);
        }
    }
    
    private static byte[] digest(byte[] in) {
        try { 
            MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
            messageDigest.reset();
            return messageDigest.digest(in);
        } catch (NoSuchAlgorithmException e) {
            // can't be here
            throw new RuntimeException(e);
        }
    }
    
    public static void main(String[] args){
        System.out.println(digest("6EpHJVo/OhmIesRC5z5chgnPrbGf03G5DW3LQj61vkJ1gONBjJrErj3TI2fRJC7zOO80RtqvpxtnpYsPnBBnQkxZ7eDQdHw4mKBmnV1rCfcveZGzy9UyjMywbej0kz4y"));
    }
}
