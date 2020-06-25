package com.zhangyong.ct.common.bean;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:28
 * @PS: 数据对象
 */
public class Data implements Val {

    public String content;


    public void setValue(Object val) {
        content= (String) val;
    }

    public Object getValue() {
        return content;
    }
}
