package com.zhangyong.ct.common.bean;

import java.io.Closeable;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 11:51
 * @PS: 消费者接口
 */
public interface Consumer extends Closeable {
    /**
     * 消费数据
     */
    void consumer();

}
