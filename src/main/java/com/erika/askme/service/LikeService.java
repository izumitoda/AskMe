package com.erika.askme.service;

import com.erika.askme.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-19 09:44
 **/
@Service
public class LikeService {
    @Autowired
    JedisService j;

    public long addLike(int entity_type,int entity_id,int user_id)
    {
        String key= new RedisKeyUtil().getLikeKey(entity_type,entity_id);
        return j.addkeyvalue(key,String.valueOf(user_id));
    }
    public long addDislike(int entity_type,int entity_id,int user_id)
    {
        String key=new RedisKeyUtil().getDislikeKey(entity_type,entity_id);
        return j.addkeyvalue(key,String.valueOf(user_id));
    }

    public int isLikeorDislike(int entity_type,int entity_id,int user_id)
    {
        String key=new RedisKeyUtil().getLikeKey(entity_type,entity_id);
        if(j.ismember(key,String.valueOf(user_id)))
            return 1;
        key=new RedisKeyUtil().getDislikeKey(entity_type,entity_id);
        if(j.ismember(key,String.valueOf(user_id)))
            return 2;
        return 0;
    }

    public long getCountLike(int entity_type,int entity_id)
    {
        String key=new RedisKeyUtil().getLikeKey(entity_type,entity_id);
        return j.getcount(key);
    }
    public long dellike(int entity_type,int entity_id,int user_id)
    {
        String key=new RedisKeyUtil().getLikeKey(entity_type,entity_id);
        return j.delvalue(key,String.valueOf(user_id));
    }

    public long delDislike(int entity_type,int entity_id,int user_id)
    {
        String key=new RedisKeyUtil().getDislikeKey(entity_type,entity_id);
        return j.delvalue(key,String.valueOf(user_id));
    }

}
