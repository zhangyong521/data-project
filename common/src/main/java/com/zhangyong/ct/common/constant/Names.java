package com.zhangyong.ct.common.constant;

import com.zhangyong.ct.common.bean.Val;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:33
 * @PS: 名称常量枚举类
 */
public enum Names implements Val {
    /**
     * 命名空间
     */
    NAMESPACE("ct");

    private String name;

    private Names(String name) {
        this.name = name;
    }

    public void setValue(Object val) {
        this.name= (String) val;
    }

    public Object getValue() {
        return null;
    }
}
