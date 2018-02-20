package com.erika.askme.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erika.askme.service.JedisService;
import com.erika.askme.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 11:10
 **/
@Service
public class EventProducer {
    @Autowired
    JedisService jedis;
    //将EVENT加入到redis队列中
    public boolean fireEvent(EventModel event)
    {
        try
        {
            String eventstring= JSONObject.toJSONString(event);
            String key= RedisKeyUtil.eventKey;
            jedis.lpush(key,eventstring);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

}
