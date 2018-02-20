package com.erika.askme.async;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 10:54
 **/
public class EventModel {
    private int userid;//事件执行者，如点赞的人
    private EventType eventype;//事件类型，如点赞
    private  int entitytype;//操作对象类型，如给评论点赞，则为评论类型
    private int entityid;//操作对象ID，如评论的ID
    private int entityownerid;//对象的拥有者，如发表该评论的用户

    public int getUserid() {
        return userid;
    }

    public EventModel setUserid(int userid) {
        this.userid = userid;
        return this;
    }

    public EventType getEventype() {
        return eventype;
    }

    public EventModel setEventype(EventType eventype) {
        this.eventype = eventype;
        return this;
    }

    public int getEntitytype() {
        return entitytype;
    }

    public EventModel setEntitytype(int entitytype) {
        this.entitytype = entitytype;
        return this;
    }

    public int getEntityid() {
        return entityid;
    }

    public EventModel setEntityid(int entityid) {
        this.entityid = entityid;
        return this;
    }

    public int getEntityownerid() {
        return entityownerid;
    }

    public EventModel setEntityownerid(int entityownerid) {
        this.entityownerid = entityownerid;
        return this;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public EventModel setMap(Map<String, String> map) {
        this.map = map;
        return this;
    }

    private Map<String,String> map=new HashMap<String, String>();

    public EventModel setkeyvalue(String key,String value)
    {
        map.put(key,value);
        return this;
    }
    public String getkeyvalue(String key)
    {
        return map.get(key);
    }


}
