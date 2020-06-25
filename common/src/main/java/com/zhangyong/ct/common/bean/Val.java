package com.zhangyong.ct.common.bean;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:10
 * @PS: 值对象接口
 */
public interface Val {

    /**
     * 设置值
     * @param val
     */
    void setValue(Object val);

    /**
     * 获取值
     * @return
     */
    Object getValue();
}
