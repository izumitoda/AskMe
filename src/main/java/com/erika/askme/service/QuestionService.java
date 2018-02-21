package com.erika.askme.service;

import com.erika.askme.controller.QuestionController;
import com.erika.askme.dao.questiondao;
import com.erika.askme.dao.userdao;
import com.erika.askme.model.Question;
import com.erika.askme.model.User;
import com.sun.deploy.net.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 23:49
 **/
@Service
public class QuestionService {
    @Autowired
    private questiondao questiondate;
    @Autowired
    private SensitiveService sensitive;

    public int getQuestionCountByUserID(int id)
    {
        return questiondate.getQuestionCountByUserId(id);
    }

    public void updateCommentCount(int count,int id)
    {
        questiondate.updateCommentCount(count,id);
    }
    public int insertquestion(Question question)
    {
        //过滤html标签
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        //敏感词过滤
        question.setTitle(sensitive.filterword(question.getTitle()));
        question.setContent(sensitive.filterword(question.getContent()));
        return questiondate.insertquestion(question)>0?question.getId():0;
    }
    public List<Question> getquestionbydate(int userid,int offset,int limit)
    {
        return questiondate.selectLatestQuestions(userid,offset,limit);
    }
    public Question getQuestionByID(int id)
    {
        return questiondate.selectQuestionById(id);
    }
}
