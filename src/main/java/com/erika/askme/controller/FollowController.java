package com.erika.askme.controller;

import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventProducer;
import com.erika.askme.async.EventType;
import com.erika.askme.model.EntityType;
import com.erika.askme.model.HostHolder;
import com.erika.askme.model.User;
import com.erika.askme.model.ViewObject;
import com.erika.askme.service.*;
import com.erika.askme.utils.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-21 15:16
 **/
@Controller
public class FollowController {
    @Autowired
    FollowService followService;

    @Autowired
    QuestionService questionService;

    @Autowired
    LikeService likeService;

    @Autowired
    CommentService commentService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;
    @Autowired
    EventProducer eventProducer;

    @RequestMapping(value = {"/followuser"}, method = {RequestMethod.POST})
    @ResponseBody
    public String followUser(@RequestParam("userId") int user_id) {
        User user = hostHolder.getuser();
        if (user == null)
            return WendaUtil.generatejson(999);
        boolean ret = followService.followSomeone(EntityType.ENTITY_USER, user_id, user.getId());
        eventProducer.fireEvent(new EventModel().setEventype(EventType.Follow).setUserid(user.getId())
                .setEntityownerid(user_id).setEntitytype(EntityType.ENTITY_USER)
                .setEntityid(user_id));
        return WendaUtil.generatejson(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER, user.getId())));
    }

    @RequestMapping(value = {"/unfollowuser"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unFollowSomeone(@RequestParam("userId") int user_id) {
        User user = hostHolder.getuser();
        if (user == null)
            return WendaUtil.generatejson(999);
        boolean ret = followService.unfollowSomeone(EntityType.ENTITY_USER, user_id, user.getId());
        return WendaUtil.generatejson(ret ? 0 : 1, String.valueOf(followService.getFolloweeCount(EntityType.ENTITY_USER, user.getId())));

    }

    @RequestMapping(value = {"/unfollowquestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String unFollowQuestion(@RequestParam("questionId") int questionid) {
        User user = hostHolder.getuser();
        if (user == null)
            return WendaUtil.generatejson(999);
        if(questionService.getQuestionByID(questionid)==null)
            return WendaUtil.generatejson(1,"问题不存在");
        boolean ret = followService.unfollowSomeone(EntityType.ENTITY_QUESTION, questionid, user.getId());
        /*eventProducer.fireEvent(new EventModel().setEventype(EventType.UnFollow).setUserid(user.getId())
                .setEntityownerid(questionService.getQuestionByID(questionid).getUserid()).setEntitytype(EntityType.ENTITY_QUESTION)
                .setEntityid(questionid));
                */
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("headUrl",user.getHeadUrl());
        map.put("id",user.getId());
        map.put("name",user.getName());
        map.put("count",followService.getFansCount(EntityType.ENTITY_QUESTION,questionid));
        return WendaUtil.getJSONString(0,map);

    }

    @RequestMapping(value = {"/followquestion"}, method = {RequestMethod.POST})
    @ResponseBody
    public String FollowQuestion(@RequestParam("questionId") int questionid) {
        User user = hostHolder.getuser();
        if (user == null)
            return WendaUtil.generatejson(999);
        if(questionService.getQuestionByID(questionid)==null)
            return WendaUtil.generatejson(1,"问题不存在");
        boolean ret = followService.followSomeone(EntityType.ENTITY_QUESTION, questionid, user.getId());
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("headUrl",user.getHeadUrl());
        map.put("id",user.getId());
        map.put("name",user.getName());
        map.put("count",followService.getFansCount(EntityType.ENTITY_QUESTION,questionid));
        eventProducer.fireEvent(new EventModel().setEventype(EventType.Follow).setUserid(user.getId())
                .setEntityownerid(questionService.getQuestionByID(questionid).getUserid()).setEntitytype(EntityType.ENTITY_QUESTION)
                .setEntityid(questionid));

        return WendaUtil.getJSONString(0,map);

    }


    @RequestMapping(value = "/followee/{user}", method = {RequestMethod.GET})
    public String getFolloweeList(@PathVariable("user")int userid, Model model) {
        model.addAttribute("name",userService.getuserbyid(userid).getName());
        model.addAttribute("FolloweeCount",followService.getFolloweeCount(EntityType.ENTITY_USER,userid));
        model.addAttribute("type",0);
        User user = hostHolder.getuser();
        if (user == null)
            return "redirect:/reglogin";
        List<Integer> followeeList = followService.getFollowees(EntityType.ENTITY_USER, userid, 0, 10);
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Integer a : followeeList) {
            ViewObject vo = new ViewObject();
            User userr = userService.getuserbyid(a);
            vo.set("user", userr);
            vo.set("fans",followService.getFansCount(EntityType.ENTITY_USER,userr.getId()));
            System.out.println(userr.getName());
            vo.set("followee",followService.getFolloweeCount(EntityType.ENTITY_USER,userr.getId()));
            vo.set("followed",followService.isFollowRelationship(EntityType.ENTITY_USER,userr.getId(),user.getId()));
            vo.set("ask",questionService.getQuestionCountByUserID(userr.getId()));
            vo.set("comment",commentService.getCommentCountByUserID(userr.getId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "follow";
    }

    @RequestMapping(value = "/follower/{user}", method = {RequestMethod.GET})
    public String getFollowerList(@PathVariable("user")int userid, Model model) {
        model.addAttribute("name",userService.getuserbyid(userid).getName());
        model.addAttribute("FolloweeCount",followService.getFansCount(EntityType.ENTITY_USER,userid));
        model.addAttribute("type",1);
        User user = hostHolder.getuser();
        if (user == null)
            return "redirect:/reglogin";
        List<Integer> followerList = followService.getFollowers(EntityType.ENTITY_USER, userid, 0, 10);
        List<ViewObject> vos = new ArrayList<ViewObject>();

        for (Integer a : followerList) {
            ViewObject vo = new ViewObject();
            User userr = userService.getuserbyid(a);
            vo.set("user", userr);
            vo.set("fans",followService.getFansCount(EntityType.ENTITY_USER,userr.getId()));
            vo.set("followee",followService.getFolloweeCount(EntityType.ENTITY_USER,userr.getId()));
            vo.set("followed",followService.isFollowRelationship(EntityType.ENTITY_USER,userr.getId(),user.getId()));
            vo.set("ask",questionService.getQuestionCountByUserID(userr.getId()));
            vo.set("comment",commentService.getCommentCountByUserID(userr.getId()));
            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "follow";
    }
}

