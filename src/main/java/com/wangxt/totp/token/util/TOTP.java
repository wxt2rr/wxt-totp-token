package com.wangxt.totp.token.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * TOTP动态码生成算法
 *
 * @author mingrisoft.shenxiaoqi
 *
 */
public class TOTP {

    private static final String MAC_NAME = "HmacSHA1";// 加密算法名称
    private static final String ENCODING = "UTF-8";// 密钥使用的字符编码
    private static final int CODE_LENGTH = 6;// 口令长度
    private static final int CODE_FLESH_TIME = 30 * 1000;// 口令刷新时间

    private TOTP() {

    }

    /**
     * 使用 HMAC-SHA1 签名方法对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @参考代码 https://blog.csdn.net/z69183787/article/details/78393216
     */
    private static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey) {
        Mac mac;// 生成一个指定 Mac 算法 的 Mac 对象
        byte[] text = null;
        try {
            byte[] data = encryptKey.getBytes(ENCODING);
            // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
            mac = Mac.getInstance(MAC_NAME);
            // 用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            text = encryptText.getBytes(ENCODING);
            return mac.doFinal(text); // 完成 Mac 操作
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 截取加密字节数组的后四个字节，并拼接成一个32位整数
     *
     * @param k 被签名的字符串
     * @param t 密钥
     * @return 经计算获得的加密数字
     *
     */
    private static int truncate(String k, String t) {
        byte[] a = HmacSHA1Encrypt(k, t);
        int s = 0;
        for (int i = 0; i < 4; i++) {// 取4个字节
            // 将数组后四个字节拼接成一个32位数字
            s += a[a.length - 1 - i] << 8 * (3 - i);
        }
        return s;
    }

    /**
     * 获取动态码
     *
     * @param key 证书
     * @return
     */
    public static String getCode(String key) {
        long scends = System.currentTimeMillis();// 当前时间毫秒数
        scends = scends / CODE_FLESH_TIME;// 获取刷新时间整数倍
        String s = String.valueOf(truncate(key, "" + scends));// 根据整数和时间进行加密计算
        int subIndex = s.length() - CODE_LENGTH;// 截取的位置
        if (subIndex < 0) {
            throw new ArrayIndexOutOfBoundsException("口令长度大于原始字符串长度");
        }
        return s.substring(subIndex);// 取后CODE_LENGTH数字
    }

    public static void main(String[] args) {
        String key = "";
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        System.out.println(getCode(key));
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
        }.start();
    }
}