package com.erika.askme.controller;

import com.erika.askme.dao.questiondao;
import com.erika.askme.model.*;
import com.erika.askme.service.CommentService;
import com.erika.askme.service.QuestionService;
import com.erika.askme.service.UserService;
import com.erika.askme.utils.WendaUtil.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        List<ViewObject> vos=new ArrayList<ViewObject>();
        List<Comment> comment=commentservice.getCommentByEntity(id, EntityType.ENTITY_QUESTION);
        for(Comment c:comment)
        {
            ViewObject vo=new ViewObject();
            vo.set("comment",c);
            vo.set("user",userservice.getuserbyid(c.getUserid()));
            int k=commentservice.getCommentCount(c.getId(),EntityType.ENTITY_COMMENT);
            vo.set("count",k);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        model.addAttribute("question",questiondata.getQuestionByID(id));
        return "detail";
    }

}
