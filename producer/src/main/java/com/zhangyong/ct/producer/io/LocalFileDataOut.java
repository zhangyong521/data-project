package com.zhangyong.ct.producer.io;

import com.zhangyong.ct.common.bean.DataOut;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:57
 * @PS: 本地文件的输出
 */
public class LocalFileDataOut implements DataOut {

    private PrintWriter writer = null;

    public LocalFileDataOut(String path) {
        setPath(path);
    }

    /**
     * 完成输出
     * @param path
     */
    public void setPath(String path) {
        try {
            writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(path), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void write(Object data) throws Exception {
        write(data.toString());
    }

    /**
     * 将数据字符串生成到文件中
     *
     * @param data
     * @throws Exception
     */
    public void write(String data) throws Exception {
        writer.println(data);
        writer.flush();
    }

    /**
     * 关闭
     * @throws IOException
     */
    public void close() throws IOException {
        if (writer!=null){
            writer.close();
        }
    }
}
