package com.erika.askme.controller;

import com.erika.askme.model.EntityType;
import com.erika.askme.model.Feed;
import com.erika.askme.model.HostHolder;
import com.erika.askme.model.User;
import com.erika.askme.service.FeedService;
import com.erika.askme.service.FollowService;
import com.erika.askme.service.JedisService;
import com.erika.askme.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 22:07
 **/
@Controller
public class FeedController {
    @Autowired
    FeedService feedService;

    @Autowired
    JedisService jedisService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @RequestMapping(path={"/pullfeed"})
    public String getPullFeeds(Model model)
    {
        User user=hostHolder.getuser();
        List<Integer> followee =new ArrayList<Integer>();
        if(user!=null)
            followee=followService.getFollowees(EntityType.ENTITY_USER,user.getId(),0,Integer.MAX_VALUE);
        List<Feed> feedList=feedService.getFeedsByUserList(Integer.MAX_VALUE,followee,10);
        model.addAttribute("feeds",feedList);
        return "feeds";
    }

    @RequestMapping(path={"/pushfeed"})
    public String getPushFeeds(Model model)
    {
        User user=hostHolder.getuser();
        List<Feed> feedList=new ArrayList<Feed>();
        String key=null;
        if(user==null)
            key= new RedisKeyUtil().getTimelineKey(0);
        else
            key=new RedisKeyUtil().getTimelineKey(user.getId());
        List<String> timeline=jedisService.lrange(key,0,10);
        for(String tmp:timeline)
        {
            Feed onefeed=feedService.getFeedById(Integer.parseInt(tmp));
            if(onefeed==null)
                continue;
            else
                feedList.add(onefeed);
        }
        model.addAttribute("feeds",feedList);
        return "feeds";
    }
}
