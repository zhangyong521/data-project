package com.zhangyong.controller;

import com.zhangyong.bean.Calllog;
import com.zhangyong.service.CalllogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-09 7:43
 * @PS: 通话日志的控制层
 */
@Controller
public class CalllogController {
    @Autowired
    private CalllogService calllogService;

    @RequestMapping("/query")
    public String query() {
        return "query";
    }

    @RequestMapping("/view")
    public String view(String tel, String calltime, Model model) {
        //查询结果
        List<Calllog> logs = calllogService.queryMonthDatas(tel, calltime);
        System.out.println(logs.size());
        model.addAttribute("calllogs",logs);
        return "view";
    }
}
