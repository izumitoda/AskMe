package com.erika.askme.dao;

import com.erika.askme.model.Question;
import org.apache.ibatis.annotations.*;

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
    @Insert({"INSERT INTO ",tablename,"(",insertfield,") values(#{title},#{content},#{userid},#{created_date},#{comment_count})"})
    int insertquestion(Question question);

   @Select({"Select",selectfield," from ",tablename," where id=#{id}"})
   Question selectQuestionById(@Param("id") int id);

    @Select({"Select count(id) from ",tablename," where user_id=#{id}"})
    int getQuestionCountByUserId(@Param("id") int id);

   @Update(("Update question set comment_count=#{count} where id=#{id}"))
   void updateCommentCount(@Param("count") int count,@Param("id")int id);
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);
}
