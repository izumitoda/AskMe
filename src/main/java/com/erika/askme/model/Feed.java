package com.erika.askme.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 16:36
 **/
public class Feed {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }
    public JSONObject getJsondate()
    {
        return jsondata;
    }

    public void setData(String data) {
        this.data = data;
        jsondata=JSONObject.parseObject(data);
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String get(String key)
    {
        if(jsondata==null)
            return null;
        else
            return jsondata.getString(key);
    }
    private int userid;
    private int type;
    private String data;
    private Date createddate;
    private JSONObject jsondata;

}
