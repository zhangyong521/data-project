package com.zhangyong.ct.producer.bean;

import com.zhangyong.ct.common.bean.DataIn;
import com.zhangyong.ct.common.bean.DataOut;
import com.zhangyong.ct.common.bean.Producer;
import com.zhangyong.ct.common.util.DateUtil;
import com.zhangyong.ct.common.util.NumBeUtil;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:41
 * @PS: 本地文件的生产者
 */
public class LocalFileProducer implements Producer {
    private DataIn in;
    private DataOut out;
    private volatile boolean flg = true;

    public void setIn(DataIn in) {
        this.in = in;
    }

    public void setOut(DataOut out) {
        this.out = out;
    }

    /**
     * 生产数据
     */
    public void produce() {
        try {
            //读取通讯数据
            List<Contact> contacts = in.read(Contact.class);

            while (flg) {
                //从通讯录中随机查找2个电话号码（主叫、被叫）
                int call1Index = new Random().nextInt(contacts.size());
                int call2Index;
                while (true) {
                    call2Index = new Random().nextInt(contacts.size());
                    if (call1Index != call2Index) {
                        break;
                    }
                }
                Contact call1 = contacts.get(call1Index);
                Contact call2 = contacts.get(call2Index);

                //生成随机的通知时间(2019全年通话的时间)
                String startDate = "20190101000000";
                String endDate = "20200101000000";

                long startTime = DateUtil.parse(startDate, "yyyyMMddHHmmss").getTime();
                long endTime = DateUtil.parse(endDate, "yyyyMMddHHmmss").getTime();
                //通话时间
                long calltime = startTime + (long) ((endTime - startTime) * Math.random());

                //通话时间字符串
                String callTimeString = DateUtil.format(new Date(calltime),"yyyyMMddHHmmss");

                //生成通话时长
                //生成的随机通话时间全部转化成4位数
                String duration = NumBeUtil.format(new Random().nextInt(3000), 4);
                //生成通话记录
                Calllog log = new Calllog(call1.getTel(), call2.getTel(), callTimeString, duration);
                System.out.println(log);
                //将通话记录写到数据文件中
                out.write(log);
                Thread.sleep(500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 关闭生产者
     *
     * @throws IOException
     */
    public void close() throws IOException {
        if (in != null) {
            in.close();
        }
        if (out != null) {
            out.close();
        }
    }
}
