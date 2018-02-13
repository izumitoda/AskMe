package com.erika.askme.dao;

import com.erika.askme.model.LoginTicket;
import org.apache.ibatis.annotations.*;

import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-12 19:48
 **/
@Mapper
public interface ticketdao {
    String tablename="login_ticket";
    String insertfield="user_id,ticket,expired,status";
    String selectfield="id,"+insertfield;
    @Insert({"Insert into ",tablename,"(",insertfield,") values(#{userid},#{ticket},#{expired},#{status})"})
    int insertticket(LoginTicket lgticket);

    @Select({"select ",selectfield," from ",tablename," where ticket=#{ticket}"})
    LoginTicket selectticket(String ticket);

    @Update({"Update ",tablename," set status=1 where ticket=#{ticket}"})
    void updateticket(String ticket);
}
