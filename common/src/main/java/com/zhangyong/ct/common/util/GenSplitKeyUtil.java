package com.zhangyong.ct.common.util;

import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 20:52
 * @PS: 生成分区键
 */
public class GenSplitKeyUtil {
    public static byte[][] genSplitKeys(int regionCount) {
        int splitKeyCount = regionCount - 1;
        byte[][] bs = new byte[splitKeyCount][];
        //0,1,2,3,4
        List<byte[]> bsList = new ArrayList<byte[]>();
        for (int i = 0; i < splitKeyCount; i++) {
            String splitKey = i + "|";
            System.out.println(splitKey);
            bsList.add(Bytes.toBytes(splitKey));
        }
        bsList.toArray(bs);
        return bs;
    }

    /**
     * 测试生成的分区键
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(genSplitKeys(6));
    }
}
