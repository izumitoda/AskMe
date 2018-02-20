package com.erika.askme.utils;

import com.alibaba.fastjson.JSON;
import com.erika.askme.model.User;

import com.alibaba.fastjson.JSONObject;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Tuple;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-18 14:21
 **/
public class JedisTry
{
    public static void print(int index,Object obj)
    {
        System.out.println(String.format("%d,%s",index,obj.toString()));
    }
    public static void main(String[] args)
    {
        Jedis jd=new Jedis("redis://localhost:6379/6");
        jd.flushDB();
        /*
        jd.set("hello","fsa");
        print(1,jd.get("hello"));

        String name="list";
        jd.del(name);
        jd.lpushx(name,"ifexists");
        print(0,jd.lrange(name,0,jd.llen(name)));
        for(int i=0;i<10;i++)
        {
            jd.lpush(name,"a"+String.valueOf(i),"b"+String.valueOf(i));
        }
        for(int i=11;i<20;i++)
            jd.rpush(name,"a"+String.valueOf(i));
        print(1,jd.lrange(name,0,jd.llen(name)));
        print(2,jd.lindex(name,6));
        jd.linsert(name, BinaryClient.LIST_POSITION.AFTER,"a8","hello");
        jd.linsert(name, BinaryClient.LIST_POSITION.AFTER,"a8","hello");
        print(3,jd.lrange(name,0,jd.llen(name)));
        jd.linsert(name, BinaryClient.LIST_POSITION.BEFORE,"a8","you");
        print(4,jd.lrange(name,0,jd.llen(name)));
        print(5,jd.lpop(name));
        jd.lrem(name,2,"hello");
        print(6,jd.lrange(name,0,jd.llen(name)));
        jd.lset(name,0,"me");
        print(7,jd.lrange(name,0,jd.llen(name)));
        jd.ltrim(name,0,9);
        print(9,jd.lrange(name,0,jd.llen(name)));
        */
        /*
        String hset="hset";
        jd.hset(hset,"name","ERIKA");
        jd.hset(hset,"age","21");
        jd.hset(hset,"school","fdu");
        print(1,jd.hgetAll(hset));
        print(2,jd.hget(hset,"name"));
        print(3,jd.hexists(hset,"family"));
        print(4,jd.hexists(hset,"school"));
        print(5,jd.hkeys(hset));
        print(6,jd.hlen(hset));
        print(7,jd.hdel(hset,"school"));
        jd.hsetnx(hset,"name","hello");
        jd.hsetnx(hset,"school","fduu");
        print(8,jd.hgetAll(hset));
        */
        /*
        String name="set";
        String name2="set2";
        for(int i=0;i<10;i++)
        {
            jd.sadd(name,String.valueOf(i));
            jd.sadd(name2,String.valueOf(i*i));
        }
        print(1,jd.scard(name));
        jd.srem(name,"4");
        print(2,jd.smembers(name));
        print(3,jd.smembers(name2));
        jd.smove(name2,name,"16");
        print(4,jd.smembers(name));
        print(44,jd.smembers(name2));
        print(5,jd.sismember(name,"9"));
        print(6,jd.sismember(name,"99"));
        print(7,jd.sunion(name,name2));
        print(8,jd.sinter(name,name2));
        print(9,jd.sdiff(name2,name));
        */
        String name="sortedlist";
        jd.zadd(name,100,"Erika");
        jd.zadd(name,80,"Lily");
        jd.zadd(name,60,"Ben");
        jd.zadd(name,40,"Jim");
        print(1,jd.zcard(name));
        print(2,jd.zcount(name,60,100));
        print(3,jd.zrange(name,0,2));
        print(4,jd.zrangeByScore(name,60,100));
        print(5,jd.zrevrangeByScore(name,100,80));
        print(6,jd.zscore(name,"Erika"));
        jd.zincrby(name,5,"Lily");
        print(7,jd.zrevrange(name,0,jd.zcard(name)));
        for(Tuple tuple:jd.zrevrangeByScoreWithScores(name,100,60))
        {
            print(8,tuple.getElement()+":"+String.valueOf(tuple.getScore()));
        }
        print(9,jd.zrank(name,"Lily"));
        print(10,jd.zrevrank(name,"Lily"));

        String s="zset";
        jd.zadd(s,1,"a");
        jd.zadd(s,1,"g");
        jd.zadd(s,1,"b");
        jd.zadd(s,1,"d");
        print(11,jd.zlexcount(s,"-","+"));
        print(12,jd.zlexcount(s,"[b","[c"));
        print(13,jd.zlexcount(s,"(b","[g"));
        jd.zremrangeByLex(s,"(b","[g");
        print(14,jd.zrange(s,0,3));
        print(15,jd.zrange(name,0,2));
        jd.set("pv","100");
        print(16,jd.get("pv"));

        JedisPool pool=new JedisPool("redis://localhost:6379/6");
        for(int i=0;i<10;i++)
        {
            Jedis j=pool.getResource();
           print(i,j.keys("*"));
            print(i,j.get("pv"));
            j.close();
        }

        User user=new User();
        user.setPassword("1");
        user.setHeadUrl("fd.png");
        user.setSalt("fdsa");
        user.setName("hello");
        user.setId(10);
        System.out.println(JSONObject.toJSONString(user));
        jd.set("user1",JSONObject.toJSONString(user));

        User user2= JSON.parseObject(jd.get("user1"),User.class);
        print(10,user2.getName());
    }
}
