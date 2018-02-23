package com.erika.askme.service;

import com.erika.askme.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-18 23:06
 **/
@Service
public class JedisService implements InitializingBean{
    private static final Logger logger= LoggerFactory.getLogger(JedisService.class) ;
    private JedisPool pool;
    public List<String> lrange(String key,int start,int end)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.lrange(key,start,end);
        }
        catch(Exception e)
        {
            logger.error("lrange异常"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return null;
    }
    public long lpush(String key,String value)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.lpush(key,value);
        }
        catch (Exception e)
        {
            logger.error("push失败"+e);
        }
        finally
        {
            if(j!=null)
                j.close();
        }
        return 0;
    }

    public List<String> brpop(int time,String key)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.brpop(time,key);
        }
        catch(Exception e)
        {
            logger.error("brpop出错"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return null;
    }

    public long addkeyvalue(String key,String value)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.sadd(key,value);
        }
        catch (Exception e)
        {
            logger.error("redis添加失败"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return 0;

    }

    public long delvalue(String key,String value)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.srem(key,value);
        }
        catch (Exception e)
        {
            logger.error("删除失败"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return 0;
    }
    public boolean ismember(String key,String value)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.sismember(key,value);
        }
        catch (Exception e)
        {
            logger.error("获取是否为成员失败"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return false;
    }

    public long getcount(String key)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.scard(key);
        }
        catch(Exception e)
        {
            logger.error("获取总数失败"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return 0;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/6");
    }

    public Jedis getJedis()
    {
        return pool.getResource();
    }

    public Transaction multi(Jedis jedis)
    {
        try
        {
            return jedis.multi();
        }
        catch (Exception e)
        {
            logger.error("开启事务失败"+e);

        }
        return null;
    }

    public List<Object> exec(Transaction tx,Jedis jedis)
    {
        try
        {
            return tx.exec();
        }
        catch (Exception e)
        {
            logger.error("执行事务失败"+e);

        }
        finally {
            if(tx!=null)
                try {
                    tx.close();
                }
                catch (Exception e)
                {
                    logger.error("关闭事务失败"+e);
                }
                if(jedis!=null)
                    jedis.close();
        }
        return null;
    }

    public long zadd(String key,double score,String value)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.zadd(key,score,value);
        }
        catch(Exception e)
        {
            logger.error("zadd失败"+e);
        }
        finally {
            if(j!=null)
            j.close();
        }
        return 0;
    }

    public long zrem(String key,String member)
    {
        Jedis j=null;
        try
        {
            j=pool.getResource();
            return j.zrem(member);
        }
        catch(Exception e)
        {
            logger.error("zrem失败"+e);
        }
        finally {
            if(j!=null)
                j.close();
        }
        return 0;
    }

    public boolean zismember(String key,String member)
    {
        return false;
    }
}
