package com.erika.askme.model;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 13:41
 **/
@Component
public class Message {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromid() {
        return fromid;
    }

    public void setFromid(int fromid) {
        this.fromid = fromid;
    }

    public int getToid() {
        return toid;
    }

    public void setToid(int toid) {
        this.toid = toid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConversationid() {
       return conversationid;
    }

    public void setConversationid(int from,int to) {
        if(from>to)
            this.conversationid=String.format("%d_%d",to,from);
        else
            this.conversationid=String.format("%d_%d",from,to);
    }

    public int getHasread() {
        return hasread;
    }

    public void setHasread(int hasread) {
        this.hasread = hasread;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    private int fromid;
    private int toid;
    private String content;
    private String conversationid;
    private int hasread;
    private Date createddate;

}
