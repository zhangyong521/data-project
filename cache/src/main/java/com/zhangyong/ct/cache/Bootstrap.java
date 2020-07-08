package com.zhangyong.ct.cache;

import com.zhangyong.ct.common.util.JDBCUtil;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-07 17:33
 * @PS: 启动缓存客户端，向redis中增加缓存数据
 */
public class Bootstrap {
    public static void main(String[] args) {
        //读取mysql中的数据
        Map<String, Integer> userMap = new HashMap<String, Integer>();
        Map<String, Integer> dateMap = new HashMap<String, Integer>();

        //读取用户
        Connection connection = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            connection= JDBCUtil.getConnection();

            String queryUserSql = "select id,tel from ct_user";
            pst = connection.prepareStatement(queryUserSql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String tel = rs.getString(2);
                userMap.put(tel, id);
            }
            rs.close();

            //读取时间
            String queryDateSql = "select id,year,month,day from ct_date";
            pst = connection.prepareStatement(queryDateSql);
            rs = pst.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String year = rs.getString(2);
                String month = rs.getString(3);
                if (month.length() == 1) {
                    month = "0" + month;
                }
                String day = rs.getString(4);
                if (day.length() == 1) {
                    day = "0" + day;
                }
                dateMap.put(year + month + day, id);
            }

        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pst != null) {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        //向Redis中缓存数据
        Jedis jedis = new Jedis("192.168.153.105", 6379);

        Iterator<String> keyIterator = userMap.keySet().iterator();
        while ( keyIterator.hasNext() ) {
            String key = keyIterator.next();
            Integer value = userMap.get(key);
            jedis.hset("ct_user", key, "" + value);
        }

        keyIterator = dateMap.keySet().iterator();
        while ( keyIterator.hasNext() ) {
            String key = keyIterator.next();
            Integer value = dateMap.get(key);
            jedis.hset("ct_date", key, "" + value);
        }
    }
}
