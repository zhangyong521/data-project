package com.zhangyong.bean;

/**
 * @Author: 张勇
 * @Blog: https://blog.csdn.net/zy13765287861
 * @Version: 1.0
 * @Date: 2020-07-09 7:43
 * @PS: 通话日志的实体类
 */
public class Calllog {
    private Integer id;
    private Integer telid;
    private Integer dateid;
    private Integer sumcall;
    private Integer sumduration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTelid() {
        return telid;
    }

    public void setTelid(Integer telid) {
        this.telid = telid;
    }

    public Integer getDateid() {
        return dateid;
    }

    public void setDateid(Integer dateid) {
        this.dateid = dateid;
    }

    public Integer getSumcall() {
        return sumcall;
    }

    public void setSumcall(Integer sumcall) {
        this.sumcall = sumcall;
    }

    public Integer getSumduration() {
        return sumduration;
    }

    public void setSumduration(Integer sumduration) {
        this.sumduration = sumduration;
    }
}
