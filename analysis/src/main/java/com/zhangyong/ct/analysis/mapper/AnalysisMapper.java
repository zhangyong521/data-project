package com.zhangyong.ct.analysis.mapper;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 11:27
 * @PS: 数据分析的Mapper
 */
public class AnalysisMapper extends TableMapper<Text, Text> {
    @Override
    protected void map(ImmutableBytesWritable key, Result value, Context context) throws IOException, InterruptedException {

        String rowkey = Bytes.toString(key.get());
        String[] values = rowkey.split("_");

        String call1 = values[1];
        String call2 = values[3];
        String callTime = values[2];
        String duration = values[4];

        String year = callTime.substring(0, 4);
        String month = callTime.substring(0, 6);
        String date = callTime.substring(0, 8);

        //主叫用户、年
        context.write(new Text(call1 + "_" + year), new Text(duration));
        //主叫用户、月
        context.write(new Text(call1 + "_" + month), new Text(duration));
        //主叫用户、日
        context.write(new Text(call1 + "_" + date), new Text(duration));

        //被叫用户、年
        context.write(new Text(call2 + "_" + year), new Text(duration));
        //被叫用户、月
        context.write(new Text(call2 + "_" + month), new Text(duration));
        //被叫用户、日
        context.write(new Text(call2 + "_" + date), new Text(duration));
    }
}
