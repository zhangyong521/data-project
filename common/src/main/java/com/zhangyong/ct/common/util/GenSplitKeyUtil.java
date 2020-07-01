/*
package com.zhangyong.ct.common.util;

import com.zhangyong.ct.common.constant.ValueConstant;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 20:52
 * @PS: 分区的工具类
 *//*

public class GenSplitKeyUtil {
    */
/**
     * 生成分区键
     * @param regionCount
     * @return
     *//*

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

    */
/**
     * 计算分区号
     * @param tel
     * @param date
     * @return
     *//*

    public static int genRegionNum(String tel, String date) {

        // 13301234567
        String userCode = tel.substring(tel.length() - 4);
        // 20181010120000
        String yearMonth = date.substring(0, 6);

        int userCodeHash = userCode.hashCode();
        int yearMonthHash = yearMonth.hashCode();

        // crc校验采用异或算法， hash
        int crc = Math.abs(userCodeHash ^ yearMonthHash);

        // 取模
        int regionNum = crc % ValueConstant.REGION_COUNT;

        return regionNum;

    }

    public static void main(String[] args) {
        //测试生成的分区键
        System.out.println(genSplitKeys(6));
        //测试计算分区号
        System.out.println(genRegionNum("13765287861", "20200410120000"));
    }
}
*/
