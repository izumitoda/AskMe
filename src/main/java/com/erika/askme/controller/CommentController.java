package com.erika.askme.controller;

import com.erika.askme.model.Comment;
import com.erika.askme.model.EntityType;
import com.erika.askme.model.HostHolder;
import com.erika.askme.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 09:59
 **/
@Controller
public class CommentController {
    private static final Logger logger= LoggerFactory.getLogger(CommentController.class) ;
    @Autowired
    CommentService commentservice;
    @Autowired
    HostHolder host;
    @RequestMapping(path={"/addcomment/"},method = {RequestMethod.POST})
    public String addcomment(@RequestParam("content") String content,@RequestParam("questionid") int id)
    {
        try
        {
            Comment comment=new Comment();
            comment.setContent(content);
            comment.setCreateddate(new Date());
            comment.setStatus(0);
            if(host.getuser()==null)
                comment.setUserid(999);
            else
                comment.setUserid(host.getuser().getId());
            comment.setEntityid(id);
            comment.setEntitytype(EntityType.ENTITY_QUESTION);
            commentservice.insertComment(comment);
            return "redirect:/question/"+String.valueOf(id);
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            return "redirect:/";
        }
    }
    @RequestMapping(path={"/deletequestion/{questionid}/{commentid}"},method={RequestMethod.GET})
    public String deleteQuestion(@PathVariable("commentid") int commentid, @PathVariable("questionid")int questionid)
    {
        commentservice.deleteComment(commentid,1);
        return "redirect:/question/"+String.valueOf(questionid);
    }

}
