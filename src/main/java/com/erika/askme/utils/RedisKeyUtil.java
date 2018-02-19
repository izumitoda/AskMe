package com.erika.askme.utils;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-19 09:46
 **/
public class RedisKeyUtil {
    private int entity_type;
    private int entity_id;
    public String getLikeKey(int entype,int id)
    {
        return "Like-"+String.valueOf(entype)+"-"+String.valueOf(id);
    }
    public String getDislikeKey(int entype,int id)
    {
        return "Dislike-"+String.valueOf(entype)+"-"+String.valueOf(id);
    }
}
