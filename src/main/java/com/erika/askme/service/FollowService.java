package com.erika.askme.service;

import com.erika.askme.utils.RedisKeyUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-21 10:10
 **/
@Service
public class FollowService {
    @Autowired
    JedisService jedis;

    public boolean followSomeone(int entity_type,int entity_id,int user_id)
    {
        String fansKey= new RedisKeyUtil().getWhoFollowedMeList(entity_id,entity_type);
        String followingKey=new RedisKeyUtil().getWhoIFollowListKey(user_id,entity_type);
        Jedis j=jedis.getJedis();
        Date date=new Date();
        Transaction tx=jedis.multi(j);
        tx.zadd(followingKey,date.getTime(),String.valueOf(entity_id));
        tx.zadd(fansKey,date.getTime(),String.valueOf(user_id));
        List<Object> ret=jedis.exec(tx,j);
        return ret.size()==2 && (Long)ret.get(0)>0 &&(Long)ret.get(1)>0;
    }
    public boolean unfollowSomeone(int entity_type,int entity_id,int user_id)
    {
        String fansKey= new RedisKeyUtil().getWhoFollowedMeList(entity_id,entity_type);
        String followingKey=new RedisKeyUtil().getWhoIFollowListKey(user_id,entity_type);
        Jedis j=jedis.getJedis();
        Date date=new Date();
        Transaction tx=jedis.multi(j);
        tx.zrem(followingKey,String.valueOf(entity_id));
        tx.zrem(fansKey,String.valueOf(user_id));
        List<Object> ret=jedis.exec(tx,j);
        return ret.size()==2 && (Long)ret.get(0)>0 &&(Long)ret.get(1)>0;
    }

    private List<Integer> getIDfromSets(Set<String> idset)
    {
        List<Integer> ids=new ArrayList<>();
        for(String tmp:idset)
            ids.add(Integer.parseInt(tmp));
        return ids;
    }
    public List<Integer> getFollowers(int entity_type,int entity_id,int offset,int count)
    {
        String fansKey=new RedisKeyUtil().getWhoFollowedMeList(entity_id,entity_type);
        Jedis j=jedis.getJedis();
        try
        {
            return getIDfromSets(j.zrange(fansKey,offset,count));
        }
        finally {
            if(j!=null)
            j.close();
        }
    }
    public List<Integer> getFollowees(int entity_type,int user_id,int offset,int count)
    {
        String FolloweeKeys=new RedisKeyUtil().getWhoIFollowListKey(user_id,entity_type);
        Jedis j=jedis.getJedis();
        try
        {
            return getIDfromSets(j.zrevrange(FolloweeKeys,offset,count));
        }
        finally {
            if(j!=null)
                j.close();
        }
    }

    public long getFansCount(int entity_type,int entity_id)
    {
        String fanskey=new RedisKeyUtil().getWhoFollowedMeList(entity_id,entity_type);
        Jedis j=jedis.getJedis();
        try
        {
            return j.zcard(fanskey);
        }
        finally {
            if(j!=null)
                j.close();
        }
    }

    public long getFolloweeCount(int entity_type,int user_id)
    {
        String followeeCount=new RedisKeyUtil().getWhoIFollowListKey(user_id,entity_type);
        Jedis j=jedis.getJedis();
        try
        {
            return j.zcard(followeeCount);
        }
        finally {
            if(j!=null)
                j.close();
        }
    }
    public boolean isFollowRelationship(int entity_type,int entity_id,int user_id)
    {
        String fanskey=new RedisKeyUtil().getWhoFollowedMeList(entity_id,entity_type);
        Jedis j=jedis.getJedis();
        try
        {
            return j.zscore(fanskey,String.valueOf(user_id))!=null;
        }
        finally {
            if(j!=null)
                j.close();
        }
    }
}
