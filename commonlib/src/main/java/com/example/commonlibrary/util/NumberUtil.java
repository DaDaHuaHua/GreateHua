package com.example.commonlibrary.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Created by RenTao on 16/11/3.
 */
public class NumberUtil {
    /**
     * 判断一个String是否为数字
     */
    public static boolean isNumber(String str) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("[0-9]+(\\.[0-9]+)?");
            Matcher m = p.matcher(str);
            if (m.matches()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否为小数/整数，max为小数点后面最多几位
     */
    public static boolean isDecimal(String str, int max) {
        if (StringUtil.isBlank(str)) {
            return false;
        }
        try {
            Pattern p = Pattern.compile("([1-9]\\d*|0)(\\.\\d{1," + max + "})?");
            Matcher m = p.matcher(str);
            if (m.matches()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断一个String是否为int数据
     */
    public static boolean isInteger(String cs) {
        if (StringUtil.isBlank(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 把字符串转为数字，如果不是数字，则返回0
     */
    public static int parseInt(String str) {
        if (isInteger(str)) {
            return Integer.parseInt(str);
        }
        return 0;
    }

    /**
     * 把字符串转为float，如果不是数字，则返回0
     */
    public static float parseFloat(String str) {
        if (isNumber(str)) {
            return Float.parseFloat(str);
        }
        return 0;
    }

    /**
     * 把字符串转为double，如果不是数字，则返回0
     */
    public static double parseDouble(String str) {
        if (isNumber(str)) {
            return Double.parseDouble(str);
        }
        return 0;
    }
}
