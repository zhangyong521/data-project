package com.zhangyong.ct.common.bean;

import java.io.Closeable;
import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:14
 * @PS: 数据来源
 */
public interface DataIn extends Closeable {
    /**
     * 输入位置
     *
     * @param path
     */
    void setPath(String path);

    /**
     * 读取数据
     *
     * @return
     * @throws Exception
     */
    Object read() throws Exception;

    /**
     * 定义泛型封装数据类型
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    <T extends Data> List<T> read(Class<T> clazz) throws Exception;
}
