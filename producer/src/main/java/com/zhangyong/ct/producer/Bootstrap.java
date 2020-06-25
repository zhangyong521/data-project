package com.zhangyong.ct.producer;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.zhangyong.ct.common.bean.Producer;
import com.zhangyong.ct.producer.bean.LocalFileProducer;
import com.zhangyong.ct.producer.io.LocalFileDataIn;
import com.zhangyong.ct.producer.io.LocalFileDataOut;

import java.io.IOException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:39
 * @PS: 启动对象
 */
public class Bootstrap {
    public static void main(String[] args) throws IOException {
        //构建生产者对象
        Producer producer = new LocalFileProducer();

        producer.setIn(new LocalFileDataIn("E:\\大数据线全套视频（2019年6月毕业班）\\尚硅谷大数据技术之电信客服综合案例\\2.资料\\辅助文档\\contact.log"));
        producer.setOut(new LocalFileDataOut("D:\\IO\\call.log"));

        //生产数据
        producer.produce();

        //关闭生产对象
        producer.close();
    }
}
