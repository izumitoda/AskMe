package com.erika.askme.controller;

import com.erika.askme.dao.questiondao;
import com.erika.askme.model.*;
import com.erika.askme.service.*;
import com.erika.askme.utils.WendaUtil;
import com.erika.askme.utils.WendaUtil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.erika.askme.utils.WendaUtil.MD5;
import static com.erika.askme.utils.WendaUtil.generatejson;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-13 10:33
 **/
@Controller
public class QuestionController {
    private static final Logger logger= LoggerFactory.getLogger(QuestionController.class) ;
    @Autowired
    HostHolder host;
    @Autowired
    QuestionService questiondata;
    @Autowired
    CommentService commentservice;
    @Autowired
    UserService userservice;

    @Autowired
    FollowService followService;

    @Autowired
    LikeService like;
    @RequestMapping(path={"/askquestion"},method={RequestMethod.POST})
    @ResponseBody
    public String askquestion(@RequestParam("title") String title,@RequestParam("content") String content)
    {
        try
        {
            Question question=new Question();
            question.setTitle(title);
            question.setContent(content);
            question.setComment_count(0);
            question.setCreated_date(new Date());
            if(host.getuser()==null)
                question.setUserid(999);
                //return WendaUtil.generatejson(1, "该用户不存在");
            else
                question.setUserid(host.getuser().getId());
            questiondata.insertquestion(question);
            return generatejson(0);
        }
        catch(Exception e)
        {
            logger.error("添加问题失败"+e);
            return generatejson(1,e.getMessage());
        }

    }

    @RequestMapping(path={"/question/{id}","/index/question/{id}"},method={RequestMethod.GET})
    public String getquestion(@PathVariable("id") int id,Model model)
    {
        if(questiondata.getQuestionByID(id)==null)
            return "redirect:/";
        User user=host.getuser();
        if(user==null)
            return "redirect:/reglogin?next=/question/"+String.valueOf(id);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        List<Comment> comment=commentservice.getCommentByEntity(id, EntityType.ENTITY_QUESTION);
        List<User> followuser=new ArrayList<User>();
        List<Integer> get=followService.getFollowers(EntityType.ENTITY_QUESTION,id,0,20);
        for(Integer a:get)
        {
            followuser.add(userservice.getuserbyid(a));
        }
        model.addAttribute("followusers",followuser);
        model.addAttribute("followed",followService.isFollowRelationship(EntityType.ENTITY_QUESTION,id,user.getId()));
        for(Comment c:comment)
        {
            ViewObject vo=new ViewObject();
            vo.set("comment",c);
            vo.set("user",userservice.getuserbyid(c.getUserid()));
            int k=commentservice.getCommentCount(c.getId(),EntityType.ENTITY_COMMENT);
            if(user!=null)
                vo.set("likeordiss",like.isLikeorDislike(EntityType.ENTITY_COMMENT,c.getId(),user.getId()));
            else
                vo.set("likeordiss",-1);
            vo.set("count",k);
            vo.set("zancount",like.getCountLike(EntityType.ENTITY_COMMENT,c.getId()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        model.addAttribute("question",questiondata.getQuestionByID(id));
        model.addAttribute("followercount",followService.getFansCount(EntityType.ENTITY_QUESTION,id));
        return "detail";
    }

}
