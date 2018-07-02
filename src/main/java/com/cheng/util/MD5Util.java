package com.cheng.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @Author :cheng
 * @Description:
 * @Date: created in 15:36 2018/7/1
 * @Reference:
 */
public class MD5Util {
    public static String md5(String src) {

        return DigestUtils.md5Hex(src);
    }
    private static final String salt = "1a2b3c4d";

    public static String inputPassForm(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        return  md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt) {
        String str = salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return  md5(str);
    }

    public static String inputPassToDbPass(String input, String saltDB) {
        String formPass = inputPassForm(input);
        String dbPass = formPassToDbPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassForm("123456"));
        System.out.println(formPassToDbPass("d3b1294a61a07da9b49b6e22b2cbd7f9", "1a2b3c"));
        System.out.println(inputPassToDbPass("123456", "1a2b3c"));
    }


}