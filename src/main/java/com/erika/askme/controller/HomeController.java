package com.erika.askme.controller;

import com.erika.askme.model.EntityType;
import com.erika.askme.model.Question;
import com.erika.askme.model.User;
import com.erika.askme.model.ViewObject;
import com.erika.askme.service.FollowService;
import com.erika.askme.service.QuestionService;
import com.erika.askme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 22:53
 **/
@Controller
public class HomeController {
    @Autowired
    QuestionService question;
    @Autowired
    UserService user;
    @Autowired
    FollowService followService;

    @RequestMapping(path={"/user/{userid}"})
    public String getuserquestion(Model model, @PathVariable("userid") int userid)
    {
        model.addAttribute("vos",getquestion(userid,0,10));
        return "index";
    }

    @RequestMapping(path={"/","/index"})
    public  String homepage(Model model)
    {
        model.addAttribute("vos",getquestion(0,0,10));
        return "index";
    }
    List<ViewObject> getquestion(int userid,int offset,int limit)
    {
        List<Question> questionlist=question.getquestionbydate(userid,offset,limit);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Question a:questionlist)
        {
            ViewObject vo=new ViewObject();
            vo.set("question",a);
            User userr=new User();
            userr=user.getuserbyid(a.getUserid());
            String tmo=userr.getHeadUrl();
            vo.set("user",userr);
            vo.set("followercount",followService.getFansCount(EntityType.ENTITY_QUESTION,a.getId()));
            vos.add(vo);
        }
        return vos;
    }
}
