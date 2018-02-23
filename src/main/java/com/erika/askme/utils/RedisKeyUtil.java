package com.erika.askme.utils;

import com.erika.askme.async.EventModel;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-19 09:46
 **/
public class RedisKeyUtil {
    private int entity_type;
    private int entity_id;

    public static String eventKey="EVENT-QUEUE";
    public String getLikeKey(int entype,int id)
    {
        return "Like-"+String.valueOf(entype)+"-"+String.valueOf(id);
    }
    public String getDislikeKey(int entype,int id)
    {
        return "Dislike-"+String.valueOf(entype)+"-"+String.valueOf(id);
    }
    public String getEventKey(EventModel event)
    {
        return eventKey;
    }
    public String getWhoIFollowListKey(int user_id,int entity_type)
    {
        return String.valueOf(user_id)+"'s-Follow-List"+String.valueOf(entity_type);
    }
    public String getWhoFollowedMeList(int entity_id,int entity_type)
    {
        return String.valueOf(entity_id)+"-"+String.valueOf(entity_type)+"-Fans-List";
    }
    public String getTimelineKey(int user_id)
    {
        return "TIMELINE-"+String.valueOf(user_id);
    }
}
