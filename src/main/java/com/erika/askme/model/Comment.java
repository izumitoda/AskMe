package com.erika.askme.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 09:36
 **/
@Component
public class Comment {
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getEntityid() {
        return entityid;
    }

    public void setEntityid(int entityid) {
        this.entityid = entityid;
    }

    public int getEntitytype() {
        return entitytype;
    }

    public void setEntitytype(int entitytype) {
        this.entitytype = entitytype;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    private int id;
    private int userid;
    private String content;
    private int entityid;
    private int entitytype;
    private Date createddate;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

}
