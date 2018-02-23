package com.erika.askme.async.Handler;

import com.erika.askme.async.EventHandler;
import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventType;
import com.erika.askme.model.EntityType;
import com.erika.askme.model.Feed;
import com.erika.askme.model.Question;
import com.erika.askme.model.User;
import com.erika.askme.service.*;
import com.erika.askme.utils.RedisKeyUtil;
import com.erika.askme.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 17:02
 **/
@Component
public class FeedHandler implements EventHandler {
    @Autowired
    UserService userService;
    @Autowired
    FollowService followService;
    @Autowired
    QuestionService questionService;
    @Autowired
    FeedService feedService;
    @Autowired
    JedisService jedis;

    private String buildFeedDate(EventModel event)
    {
        Map<String,Object> map=new HashMap<String, Object>();
        User actor=userService.getuserbyid(event.getUserid());
        if(actor==null)
            return null;
        map.put("userid",String.valueOf(actor.getId()));
        map.put("userHead",actor.getHeadUrl());
        map.put("username",actor.getName());
        if(event.getEventype()==EventType.COMMENT)
        {
            Question q=questionService.getQuestionByID(event.getEntityid());
            if(q==null)
                return null;
            map.put("questionid",q.getId());
            map.put("questiontitle",q.getTitle());
            map.put("askperson",userService.getuserbyid(q.getUserid()).getName());
            return WendaUtil.getJSONString(0,map);
        }
        return null;
    }
    @Override
    public void doHandle(EventModel event) {
        Feed feed=new Feed();
        feed.setCreateddate(new Date());
        feed.setType(event.getEventype().getValue());
        feed.setUserid(event.getUserid());
        feed.setData(buildFeedDate(event));
        if(feed.getData()==null)
            return;
        else
            feedService.insertIntoFeed(feed);

        List<Integer> followers=followService.getFollowers(EntityType.ENTITY_USER,feed.getUserid(),0,Integer.MAX_VALUE);
        followers.add(0);
        for(Integer a:followers)
        {
            String key= new RedisKeyUtil().getTimelineKey(a);
            jedis.lpush(key,String.valueOf(feed.getId()));
        }
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.ASK,EventType.COMMENT,EventType.LIKE,EventType.Follow);
    }
}
