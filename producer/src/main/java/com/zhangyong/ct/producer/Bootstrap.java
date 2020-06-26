package com.zhangyong.ct.producer;

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
        if (args.length < 2) {
            System.out.println("参数不正确，请按照指定格式传递：java -jar Produce.jar path1 path2");
            System.exit(1);
        }

        //构建生产者对象
        Producer producer = new LocalFileProducer();


       /*
        producer.setIn(new LocalFileDataIn("D:\\IO\\contact.log"));
        producer.setOut(new LocalFileDataOut("D:\\IO\\call.log"));
        */

        producer.setIn(new LocalFileDataIn(args[0]));
        producer.setOut(new LocalFileDataOut(args[1]));

        //生产数据
        producer.produce();

        //关闭生产对象
        producer.close();
    }
}
