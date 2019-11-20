package com.jdownload.pool.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public static String hash(String string) {
        MessageDigest m;
        try {
            m = MessageDigest.getInstance("MD5");
            m.update(string.getBytes(), 0, string.length());
            return new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
