package com.zhangyong.ct.common.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 20:12
 * @PS: 读取配置文件的信息的工具类（此项目没有用到）
 */
public class ConfigUtil {
    private static Map<String, String> valueMap = new HashMap<String, String>();

    static {
        ResourceBundle ct = ResourceBundle.getBundle("ct");
        Enumeration<String> enums = ct.getKeys();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = ct.getString(key);
            valueMap.put(key, value);
        }
    }

    public static String getVal(String key) {
        return valueMap.get(key);
    }

    /**
     * 测试工具类
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(ConfigUtil.getVal("ct.cf.caller"));
    }
}
