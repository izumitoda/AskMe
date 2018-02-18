package com.erika.askme.service;

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
    private JedisPool pool;

    public long addkeyvalue(String key,String value)
    {
        return 0;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        pool=new JedisPool("redis://localhost:6379/6");
    }
}
