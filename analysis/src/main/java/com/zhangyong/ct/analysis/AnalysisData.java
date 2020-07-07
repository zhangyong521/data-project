package com.zhangyong.ct.analysis;

import com.zhangyong.ct.analysis.tool.AnalysisTool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 11:12
 * @PS: 分析数据
 */
public class AnalysisData {
    public static void main(String[] args) throws Exception {
        int result = ToolRunner.run(new AnalysisTool(), args);
    }
}
