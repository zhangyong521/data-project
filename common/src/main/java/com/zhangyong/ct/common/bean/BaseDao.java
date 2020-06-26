package com.zhangyong.ct.common.bean;


import com.zhangyong.ct.common.constant.Names;
import com.zhangyong.ct.common.util.GenSplitKeyUtil;
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

import java.io.IOException;

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
            byte[][] splitKeys = GenSplitKeyUtil.genSplitKeys(regionCount);
            admin.createTable(tableDescriptor, splitKeys);
        }
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
