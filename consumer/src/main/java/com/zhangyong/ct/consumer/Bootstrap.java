package com.zhangyong.ct.consumer;

import com.zhangyong.ct.common.bean.Consumer;
import com.zhangyong.ct.consumer.bean.CalllogConsumer;

import java.io.IOException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 11:49
 * @PS: 启动消费者（消费flume传来的数据）
 * 使用kafka的消费者获取Flume采集的数据
 * 将数据存储到HBase中去
 */
public class Bootstrap {
    public final static String ABC = "ABC";
    public static void main(String[] args) throws IOException {
        //创建消费者
        Consumer consumer = new CalllogConsumer();

        //消费数据
        consumer.consumer();

        //关闭资源
        consumer.close();
    }
}
