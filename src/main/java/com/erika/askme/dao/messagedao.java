package com.erika.askme.dao;

import com.erika.askme.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 13:44
 **/
@Mapper
public interface messagedao {
    String tablename="message";
    String insertfield="fromid,toid,conversation_id,content,created_date,has_read";
    String selectfield="id,"+insertfield;

    @Insert({"INSERT INTO message(",insertfield,") values(#{fromid},#{toid},#{conversationid},#{content},#{createddate},#{hasread})"})
    int insertmessage(Message msg);

    @Select({"Select ",selectfield," from message where fromid=#{id} order by created_date desc"})
    List<Message> sendMessage(@Param("id") int id);

    @Select({"Select ",selectfield," from message where toid=#{id} and has_read=0 order by created_date desc"})
    List<Message> getUnreadMessage(@Param("id")int id);

    @Select({"Select ",selectfield," from message where toid=#{id} order by created_date desc"})
    List<Message> getMessage(@Param("id")int id);

    @Select({"Select ",selectfield," from message where conversation_id=#{id} order by created_date desc"})
    List<Message> getConversation(@Param("id") String id);

    @Update({"Update message set has_read=1 where id=#{id}"})
    void readMessage(@Param("id") int id);

    @Select({"select * from message a where created_date=(select max(created_date) from message where conversation_id=a.conversation_id) and (toid=#{myid} or fromid=#{myid}) order by created_date desc"})
    List<Message> listmessageget(@Param("myid") int id);

    @Select({"SELECT count(id) from ",tablename," where conversation_id=#{id}"})
    int getAllCountByConversationId(@Param("id")String id);

    @Select({"SELECT count(id) from ",tablename," where conversation_id=#{id} and toid=#{myid} and has_read=0"})
    int getAllUnreadCountByConversationId(@Param("id")String id,@Param("myid") int myid);
}
