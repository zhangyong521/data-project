package com.zhangyong.service.impl;

import com.zhangyong.bean.Calllog;
import com.zhangyong.dao.CalllogDao;
import com.zhangyong.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-09 7:45
 * @PS: 通话日志数据业务逻辑实现
 */
@Service
public class CalllogServiceImpl implements CalllogService {
    @Autowired
    private CalllogDao calllogDao;

    /**
     * 查询用户指定时间的通话统计信息
     *
     * @param tel
     * @param calltime
     * @return
     */
    @Override
    public List<Calllog> queryMonthDatas(String tel, String calltime) {
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("tel", tel);
        if (calltime.length() > 4) {
            calltime = calltime.substring(0, 4);
        }
        paraMap.put("year", calltime);
        System.out.println(paraMap);
        return calllogDao.queryMonthDatas(paraMap);
    }
}
