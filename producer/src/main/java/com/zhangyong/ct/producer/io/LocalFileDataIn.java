package com.zhangyong.ct.producer.io;

import com.zhangyong.ct.common.bean.Data;
import com.zhangyong.ct.common.bean.DataIn;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-22 20:56
 * @PS: 本地文件的输入
 */
public class LocalFileDataIn implements DataIn {

    private BufferedReader reader = null;

    public LocalFileDataIn(String path) {
        setPath(path);
    }

    /**
     * 流式处理
     * @param path
     */
    public void setPath(String path) {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object read() throws Exception {
        return null;
    }

    /**
     * 泛型，读取数据，返回集合
     * @param clazz
     * @param <T>
     * @return
     * @throws Exception
     */
    public <T extends Data> List<T> read(Class<T> clazz) throws Exception {
        List<T> ts = new ArrayList<T>();

        try {
            //从数据文件中读取所有的数据
            String line = null;
            while ((line = reader.readLine()) != null) {
                //将数据转换为指定类型的对象，封装为集合返回
                T t = clazz.newInstance();
                t.setValue(line);
                ts.add(t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ts;
    }

    /**
     * 关闭资源
     * @throws IOException
     */
    public void close() throws IOException {
        if(reader!=null){
            reader.close();
        }
    }
}
