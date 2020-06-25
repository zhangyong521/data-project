package com.zhangyong.ct.common.bean;

import java.io.Closeable;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:14
 * @PS: 输出位置
 */
public interface DataOut extends Closeable {
    /**
     * 输出位置
     *
     * @param path
     */
    void setPath(String path);

    /**
     * 输出数据
     *
     * @param data
     * @throws Exception
     */
    void write(Object data) throws Exception;

    /**
     * 输出数据（类型不一样）
     *
     * @param data
     * @throws Exception
     */
    void write(String data) throws Exception;
}
