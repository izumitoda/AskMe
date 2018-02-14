package com.erika.askme.dao;

import com.erika.askme.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 09:38
 **/
@Mapper
public interface commentdao {
    String tablename="comment";
    String insertfield="content,user_id,created_date,entity_id,entity_type,status";
    String selectfield="id,"+insertfield;

    @Insert({"INSERT INTO",tablename,"(",insertfield,") values(#{content},#{userid},#{createddate},#{entityid},#{entitytype},#{status})"})
    int insertcomment(Comment comment);

    @Select({"Select ",selectfield, "from ",tablename," where entity_id=#{id} and entity_type=#{type} and status=0 order by created_date desc"})
    List<Comment> selectbyentity(@Param("id") int id,@Param("type") int type);

    @Select({"Select ",selectfield, "from ",tablename," where id=#{id} and status=0"})
    Comment selectbycommentid(@Param("id") int id);

    @Select({"SELECT count(id) from ",tablename," where entity_id=#{id} and entity_type=#{type} and status=0"})
    int getCommentCount(@Param("id")int id,@Param("type")int type);

    @Select({"Select ",selectfield, "from ",tablename," where user_id=#{user_id} and staus=0 order by created_date desc " })
    List<Comment> selectbyuser(@Param("user_id") int user_id);

    @Update({"Update ",tablename," set status=#{status} where id=#{id}"})
    void deletecomment(@Param("status") int status,@Param("id") int id);
}
