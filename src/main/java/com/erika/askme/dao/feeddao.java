package com.erika.askme.dao;

import com.erika.askme.model.Feed;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 16:37
 **/
@Mapper
public interface feeddao {
    String tablename="feed";
    String insertfield="type,user_id,created_date,data";
    String selectfield="id,"+insertfield;

    @Insert({"Insert into feed(",insertfield,") values(#{type},#{userid},#{createddate},#{data})"})
    int insertFeed(Feed feed);

    @Select({"Select ",selectfield," from feed where id=#{id}"})
    Feed selectFeedById(@Param("id")int id);

    List<Feed> selectFeedsByUserList(@Param("maxId")int maxId,@Param("userIds")List<Integer> userIds,@Param("count")int count);


}
