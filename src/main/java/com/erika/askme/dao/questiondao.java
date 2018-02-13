package com.erika.askme.dao;

import com.erika.askme.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 21:21
 **/
@Mapper
public interface questiondao {
    String tablename="question";
    String insertfield="title,content,user_id,created_date,comment_count";
    String selectfield="id,"+insertfield;
    @Insert({"INSERT INTO ",tablename,"(",insertfield,") values(#{title},#{content},#{user_id},#{created_date},#{comment_count})"})
    int insertquestion(Question question);

   @Select({"Select",selectfield," from ",tablename," where id=#{id}"})
   Question selectQuestionById(@Param("id") int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
}
