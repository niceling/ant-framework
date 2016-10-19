package com.antteam.test.framework.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {

    // 加密连接串
    public static final String SGIN = "_";
    /**
     * 日志对象
     */
    private static final Logger log = LoggerFactory.getLogger(MD5Util.class);

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    /**
     * 生成
     * 
     * @param strs 字符串组
     * @return
     */
    public static final String calcs(String... strs) {
        String tempstr = "";
        for (String string : strs) {
            tempstr += string + SGIN;
        }
        return calc(tempstr);
    }


    public static String MD5(String s) {
    	byte[] btInput = null;
		try {
			btInput = s.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("MD5:", e);
			return null;
		}
		
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = null;
		try {
			mdInst = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("MD5:", e);
			return null;
		}
		
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char[] str = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }

    public static final String calc(String ss) {
        String s = ss == null ? "" : ss;
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        byte[] strTemp = s.getBytes();
        MessageDigest mdTemp = null;;
		try {
			mdTemp = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			log.error("calc:", e);
			return null;
		}
		
        mdTemp.update(strTemp);
        byte[] md = mdTemp.digest();
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
