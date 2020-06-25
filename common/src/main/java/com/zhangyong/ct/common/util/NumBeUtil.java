package com.zhangyong.ct.common.util;

import java.text.DecimalFormat;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-23 7:31
 * @PS: 转换数字的工具类->10->0000010
 */
public class NumBeUtil {
    /**
     * 将数字格式化字符串
     *
     * @param num
     * @param length
     * @return
     */
    public static String format(int num, int length) {

        /**
         * 遍历length的位数
         */
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 1; i <= length; i++) {
            stringBuffer.append("0");
        }

        DecimalFormat df = new DecimalFormat(stringBuffer.toString());
        return df.format(num);
    }

    public static void main(String[] args) {
        System.out.println(format(10, 10));
    }
}
