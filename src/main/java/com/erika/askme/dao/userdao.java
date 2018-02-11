package com.erika.askme.dao;

import com.erika.askme.model.User;
import org.apache.ibatis.annotations.*;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 14:46
 **/
@Mapper
public interface userdao  {
    String tablename="user";
    String insertfield="name,password,salt,head_url";
    String selectfield="id,"+insertfield;
    @Insert({"INSERT INTO ",tablename,"("+insertfield+") Values(#{name},#{password},#{salt},#{head_url})"})
    int insertuser(User user);

    @Select({"Select ",selectfield," from ",tablename," where id=#{id}"})
    User selectuser(int id);

    @Update({"Update ",tablename," set password=#{password} where id=#{id}"})
    void updateuser(User user);

    @Delete({"Delete from ",tablename," where id=#{id}"})
    void deleteuser(int id);
}
