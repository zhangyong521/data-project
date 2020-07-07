package com.zhangyong.ct.analysis.reducer;

import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 11:27
 * @PS: 分析数据的reducer
 */
public class AnalysisReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        int sumCall = 0;
        int sumDuration = 0;

        for (Text value : values) {
            int duration = Integer.parseInt(value.toString());
            sumDuration = sumDuration + duration;
            sumCall++;
        }
        context.write(key, new Text(sumCall + "_" + sumDuration));
    }

}
