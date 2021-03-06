package com.yuntao.zhushou.model.vo;

import java.io.Serializable;

/**
 * Created by shan on 2016/5/5.
 */
public class LogMessageVo implements Serializable {

    private String id;

    private String key;

    private String time;

    private String lastTime;

    private Integer type;  //0 master message,1 sql,2 cache

    private String message;

    private String dataMsg;

    private String sql;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDataMsg() {
        return dataMsg;
    }

    public void setDataMsg(String dataMsg) {
        this.dataMsg = dataMsg;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
