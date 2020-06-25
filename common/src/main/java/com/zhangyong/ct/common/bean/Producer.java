package com.zhangyong.ct.common.bean;

import java.io.Closeable;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:13
 * @PS: 生产接口
 */
public interface Producer extends Closeable {

    /**
     * 数据来源
     *
     * @param in
     */
    void setIn(DataIn in);

    /**
     * 输出位置
     *
     * @param out
     */
    void setOut(DataOut out);

    /**
     * 生产数据
     */
    void produce();
}
