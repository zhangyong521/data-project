package com.zhangyong.ct.analysis.reducer;

import com.zhangyong.ct.analysis.kv.AnalysisKey;
import com.zhangyong.ct.analysis.kv.AnalysisValue;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-08 15:24
 * @PS: 分析数据Reducer
 */
public class AnalysisBeanReducer extends Reducer<AnalysisKey, Text, AnalysisKey, AnalysisValue> {
    @Override
    protected void reduce(AnalysisKey key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        int sumCall = 0;
        int sumDuration = 0;

        for (Text value : values) {
            int duration = Integer.parseInt(value.toString());
            sumDuration = sumDuration + duration;

            sumCall++;
        }

        context.write(key, new AnalysisValue("" + sumCall, "" + sumDuration));
    }
}
