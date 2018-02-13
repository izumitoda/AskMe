package com.erika.askme.service;

import com.erika.askme.dao.questiondao;
import com.erika.askme.dao.userdao;
import com.erika.askme.model.Question;
import com.erika.askme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public List<Question> getquestionbydate(int userid,int offset,int limit)
    {
        return questiondate.selectLatestQuestions(userid,offset,limit);
    }
}
