package com.erika.askme.service;

import com.erika.askme.controller.CommentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
}
