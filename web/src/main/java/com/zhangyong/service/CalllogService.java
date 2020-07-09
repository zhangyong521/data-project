package com.zhangyong.service;

import com.zhangyong.bean.Calllog;

import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-09 7:44
 * @PS: 通话日志数据业务逻辑
 */
public interface CalllogService {
    List<Calllog> queryMonthDatas(String tel, String calltime);
}
