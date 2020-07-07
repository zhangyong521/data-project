package com.zhangyong.ct.analysis.io;

import com.zhangyong.ct.common.util.JDBCUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.JobContext;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.OutputFormat;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputCommitter;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 11:39
 * @PS: mysql数据格式化输入对象
 */
public class MySqlOutputFormat extends OutputFormat<Text, Text> {

    protected static class MySqlRecordWriter extends RecordWriter<Text, Text> {

        private Connection connection = null;

        public MySqlRecordWriter() {
            //获取资源
            connection = JDBCUtil.getConnection();
        }

        /**
         * 输出数据
         *
         * @param text
         * @param value
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void write(Text text, Text value) throws IOException, InterruptedException {
            String[] values = value.toString().split("_");
            String sumCall = values[0];
            String sumDuration = values[1];

            PreparedStatement pst = null;

            try {
                String insertSQL = "insert into ct_call( telid, dateid, sumcall, sumduration ) values ( ?, ?, ?, ? )";
                pst = connection.prepareStatement(insertSQL);
                pst.setInt(1, 2);
                pst.setInt(2, 3);
                pst.setInt(3, Integer.parseInt(sumCall));
                pst.setInt(4, Integer.parseInt(sumDuration));
                pst.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                if (pst!=null){
                    try {
                        pst.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 释放资源
         *
         * @param context
         * @throws IOException
         * @throws InterruptedException
         */
        @Override
        public void close(TaskAttemptContext context) throws IOException, InterruptedException {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext context) throws IOException, InterruptedException {
        return new MySqlRecordWriter();
    }

    @Override
    public void checkOutputSpecs(JobContext context) throws IOException, InterruptedException {

    }

    private FileOutputCommitter committer = null;

    public static Path getOutputPath(JobContext job) {
        String name = job.getConfiguration().get(FileOutputFormat.OUTDIR);
        return name == null ? null : new Path(name);
    }

    /**
     * 提交任务给MapReduce
     *
     * @param context
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public OutputCommitter getOutputCommitter(TaskAttemptContext context) throws IOException, InterruptedException {
        if (committer == null) {
            Path output = getOutputPath(context);
            committer = new FileOutputCommitter(output, context);
        }
        return committer;
    }
}
