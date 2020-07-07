package com.zhangyong.ct.analysis.tool;

import com.zhangyong.ct.analysis.io.MySqlOutputFormat;
import com.zhangyong.ct.analysis.mapper.AnalysisMapper;
import com.zhangyong.ct.analysis.reducer.AnalysisReducer;
import com.zhangyong.ct.common.constant.Names;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.JobStatus;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.io.Text;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 11:14
 * @PS: 分析数据的工具类
 */
public class AnalysisTool implements Tool {

    /**
     * 分析逻辑
     *
     * @param strings
     * @return
     * @throws Exception
     */
    public int run(String[] strings) throws Exception {
        Job job = Job.getInstance();
        job.setJarByClass(AnalysisTool.class);

        Scan scan = new Scan();
        scan.addFamily(Bytes.toBytes(Names.CF_CALLER.getValue()));

        //mapper
        TableMapReduceUtil.initTableMapperJob(
                Names.TABLE.getValue(),
                scan,
                AnalysisMapper.class,
                Text.class,
                Text.class,
                job
        );

        //reducer
        job.setReducerClass(AnalysisReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //outputformat
        job.setOutputFormatClass(MySqlOutputFormat.class);

        boolean flg = job.waitForCompletion(true);
        if (flg) {
            return JobStatus.State.SUCCEEDED.getValue();
        } else {
            return JobStatus.State.FAILED.getValue();
        }

    }

    public void setConf(Configuration configuration) {

    }

    public Configuration getConf() {
        return null;
    }
}
