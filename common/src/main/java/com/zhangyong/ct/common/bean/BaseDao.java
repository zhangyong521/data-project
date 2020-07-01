package com.zhangyong.ct.common.bean;


import com.zhangyong.ct.common.api.Column;
import com.zhangyong.ct.common.api.Rowkey;
import com.zhangyong.ct.common.api.TableRef;
import com.zhangyong.ct.common.constant.Names;
import com.zhangyong.ct.common.constant.ValueConstant;
import com.zhangyong.ct.common.util.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.NamespaceDescriptor;
import org.apache.hadoop.hbase.NamespaceNotFoundException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import javax.swing.plaf.synth.Region;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-06-25 14:55
 * @PS: 封装方法
 * 基础的访问对象
 * <p>
 * 生成电话号码时间：java -jar producer.jar /opt/module/datas/contact.log /opt/module/datas/call.log
 * flume：bin/flume-ng agent -c conf/ -n a1 -f /opt/module/datas/flume-kafka.conf
 */
public abstract class BaseDao {

    private ThreadLocal<Connection> connHolder = new ThreadLocal<Connection>();
    private ThreadLocal<Admin> adminHolder = new ThreadLocal<Admin>();

    protected void start() throws Exception {
        getConnection();
        getAdmin();
    }

    protected void end() throws Exception {
        Admin admin = getAdmin();
        if (admin != null) {
            admin.close();
            adminHolder.remove();
        }

        Connection conn = getConnection();
        if (conn != null) {
            conn.close();
            connHolder.remove();
        }
    }

    /**
     * NX：表示有没有什么不管，没有我就创建
     * 创建命名空间，如果命名空间存在，不需要创建，否则，创建新的
     */
    protected void createNamespaceNX(String namespace) throws IOException {
        Admin admin = getAdmin();
        try {
            admin.getNamespaceDescriptor(namespace);
        } catch (NamespaceNotFoundException e) {
            //创建新的命名空间
            NamespaceDescriptor namespaceDescriptor = NamespaceDescriptor.create(namespace).build();
            admin.createNamespace(namespaceDescriptor);
        }
    }

    /**
     * XX:表示有表删除再创建，没有直接创建表
     * 创建表，如果表存在创建新的，没有就创建
     */
    protected void createTableXX(String name, String... families) throws IOException {
        createTableXX(name, null, families);
    }

    protected void createTableXX(String name, Integer regionCount, String... families) throws IOException {
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);
        if (admin.tableExists(tableName)) {
            //表存在，删除表
            deleteTable(name);
        }
        //创建表
        createTable(name, regionCount, families);
    }

    private void createTable(String name, Integer regionCount, String... families) throws IOException {
        Admin admin = getAdmin();
        TableName tableName = TableName.valueOf(name);
        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        if (families == null || families.length == 0) {
            families = new String[1];
            families[0] = Names.CF_INFO.getValue();
        }
        for (String family : families) {
            HColumnDescriptor columnDescriptor = new HColumnDescriptor(family);
            tableDescriptor.addFamily(columnDescriptor);
        }
        //增加预分区
        if (regionCount == null || regionCount <= 1) {
            admin.createTable(tableDescriptor);
        } else {
            //分区键
            byte[][] splitKeys = genSplitKeys(regionCount);
            admin.createTable(tableDescriptor, splitKeys);
        }
    }

    /**
     * 生成分区键
     *
     * @param regionCount
     * @return
     */
    public byte[][] genSplitKeys(int regionCount) {
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
     * 获取查询时的startrow，stoprow集合
     *
     * @param tel
     * @param start
     * @param end
     * @return
     */
    protected List<String[]> getStartStorRowkeys(String tel, String start, String end) {
        List<String[]> rowkeyss = new ArrayList<String[]>();

        String startTime = start.substring(0, 6);
        String endTime = end.substring(0, 6);

        //转化为日历
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(DateUtil.parse(startTime, "yyyyMM"));
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(DateUtil.parse(endTime, "yyyyMM"));

        while (startCal.getTimeInMillis() <= endCal.getTimeInMillis()) {
            //当前时间
            String nowTime = DateUtil.format(startCal.getTime(), "yyyyMM");

            int regionNum = genRegionNum(tel, nowTime);

            //1_133_202003 ~ 1_133_202003|
            String startRow = regionNum + "_" + tel + "_" + nowTime;
            String stopRow = startRow + "|";

            String[] rowkeys = {startRow, stopRow};
            rowkeyss.add(rowkeys);

            //月份+1
            startCal.add(Calendar.MONTH, 1);
        }

        return rowkeyss;
    }

    /**
     * 计算分区号
     *
     * @param tel
     * @param date
     * @return
     */
    public int genRegionNum(String tel, String date) {
        // 13301234567
        String userCode = tel.substring(tel.length() - 4);
        // 20201010120000
        String yearMonth = date.substring(0, 6);
        int userCodeHash = userCode.hashCode();
        int yearMonthHash = yearMonth.hashCode();
        // crc校验采用异或算法， hash
        int crc = Math.abs(userCodeHash ^ yearMonthHash);
        // 取模
        int regionNum = crc % ValueConstant.REGION_COUNT;
        return regionNum;

    }

    /**
     * 增加对象：自动封装数据，将对象数据直接保存到HBase中
     *
     * @param obj
     * @throws Exception
     */
    protected void putData(Object obj) throws Exception {
        //反射
        Class clazz = obj.getClass();
        TableRef tableRef = (TableRef) clazz.getAnnotation(TableRef.class);
        String tableName = tableRef.value();
        Field[] fs = clazz.getDeclaredFields();
        String stringRowkey = "";
        for (Field f : fs) {
            Rowkey rowkey = f.getAnnotation(Rowkey.class);
            if (rowkey != null) {
                f.setAccessible(true);
                stringRowkey = (String) f.get(obj);
                break;
            }
        }
        Connection conn = getConnection();
        Table table = conn.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(stringRowkey));
        for (Field f : fs) {
            Column column = f.getAnnotation(Column.class);
            if (column != null) {
                String family = column.family();
                String colName = column.column();
                if (colName == null || "".equals(colName)) {
                    colName = f.getName();
                }
                f.setAccessible(true);
                String value = (String) f.get(obj);

                put.addColumn(Bytes.toBytes(family), Bytes.toBytes(colName), Bytes.toBytes(value));
            }

        }
        //增加数据
        table.put(put);
        //关闭表
        table.close();
    }

    /**
     * 增加数据
     *
     * @param name
     * @param put
     */
    protected void putData(String name, Put put) throws IOException {
        //获取表对象
        Connection conn = getConnection();
        Table table = conn.getTable(TableName.valueOf(name));
        //增加数据
        table.put(put);
        //关闭表
        table.close();
    }

    /**
     * 删除表格
     *
     * @param name
     * @throws IOException
     */
    protected void deleteTable(String name) throws IOException {
        TableName tableName = TableName.valueOf(name);
        Admin admin = getAdmin();
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    /**
     * 获取连接
     * 没有创建新的，
     *
     * @return
     */
    protected Connection getConnection() throws IOException {
        //防止之前访问过
        Connection conn = connHolder.get();
        if (conn == null) {
            Configuration conf = HBaseConfiguration.create();
            conn = ConnectionFactory.createConnection(conf);
            connHolder.set(conn);
        }
        return conn;
    }

    /**
     * 获取连接对象
     *
     * @return
     */
    protected synchronized Admin getAdmin() throws IOException {
        Admin admin = adminHolder.get();
        if (admin == null) {
            admin = getConnection().getAdmin();
            adminHolder.set(admin);
        }
        return admin;
    }

}
