package com.erika.askme.service;

import com.erika.askme.dao.commentdao;
import com.erika.askme.model.Comment;
import com.erika.askme.model.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 09:53
 **/
@Service
public class CommentService {
    @Autowired
    commentdao commentdata;
    @Autowired
    QuestionService question;
    @Autowired
    private SensitiveService sensitive;
    public int getCommentCount(int id,int type)
    {
        return commentdata.getCommentCount(id,type);
    }
    public List<Comment> getCommentByEntity(int id,int type)
    {
        return commentdata.selectbyentity(id,type);
    }
    public List<Comment> getCommentByUser(int id)
    {
        return commentdata.selectbyuser(id);
    }
    public int insertComment(Comment comment)
    {
        //过滤html标签
        comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
        //敏感词过滤
        comment.setContent(sensitive.filterword(comment.getContent()));
        if(comment.getEntitytype()== EntityType.ENTITY_QUESTION)
        {
            int k=comment.getEntityid();
            question.updateCommentCount(getCommentCount(k,EntityType.ENTITY_QUESTION)+1,k);
        }
        return commentdata.insertcomment(comment);
    }
    public int getCommentCountByUserID(int id)
    {
        return commentdata.getCommentCountByUserId(id);
    }
    public Comment selectCommentById(int id)
    {
        return commentdata.selectbycommentid(id);
    }
    public void deleteComment(int id,int status)
    {
        Comment comment=selectCommentById(id);
        if(comment.getEntitytype()== EntityType.ENTITY_QUESTION)
        {
            int k=comment.getEntityid();
            question.updateCommentCount(getCommentCount(k,EntityType.ENTITY_QUESTION)-1,k);
        }
        commentdata.deletecomment(status,id);
    }
}
