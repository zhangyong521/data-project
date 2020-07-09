package com.zhangyong.dao;

import com.zhangyong.bean.Calllog;

import java.util.List;
import java.util.Map;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-09 7:44
 * @PS: 通话日志数据访问对象
 */
public interface CalllogDao {
    /**
     * 查询日志数据
     * @param paraMap
     * @return
     */
    List<Calllog> queryMonthDatas(Map<String, Object> paraMap);
}
