package com.erika.askme.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erika.askme.controller.CommentController;
import com.erika.askme.service.JedisService;
import com.erika.askme.utils.RedisKeyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 11:10
 **/
@Service
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    @Autowired
    JedisService jedis;
    private static final Logger logger= LoggerFactory.getLogger(EventConsumer.class) ;
    private Map<EventType,List<EventHandler>> eventConsumerMap=new HashMap<EventType,List<EventHandler>>();
    //一种类型的EventType进来，就寻找这件Event所需要的Handler的列表
    private ApplicationContext applicationContext;
    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
        //找到所有的EventHandler
        if(beans!=null)
        {
            for(Map.Entry<String,EventHandler> entry:beans.entrySet())
            {
                List<EventType> tmp=entry.getValue().getSupportEventType();
                for(EventType a:tmp)
                {
                    if(eventConsumerMap.containsKey(a)==false)
                    {
                        eventConsumerMap.put(a,new ArrayList<EventHandler>());
                    }
                        eventConsumerMap.get(a).add(entry.getValue());
                }
            }
        }
        //初始化Map<EventType,List<EventHandler>>将Event类型和需要用到的EventHandler关联起来

        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    String key= RedisKeyUtil.eventKey;
                    List<String> eventlist=jedis.brpop(0,key);
                    for(String tmp:eventlist)
                    {
                        if(tmp.equals(key))
                            continue;
                        EventModel model= JSON.parseObject(tmp,EventModel.class);
                        if(!eventConsumerMap.containsKey(model.getEventype()))
                        {
                            logger.error("不能识别的事件");
                            continue;
                        }
                            for(EventHandler handler:eventConsumerMap.get(model.getEventype()))
                        {
                            handler.doHandle(model);
                        }
                    }
                }
            }
        });
        thread.start();

    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
