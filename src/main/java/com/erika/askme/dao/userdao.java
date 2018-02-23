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
    String insertfield="name,password,salt,head_url,activation,code";
    String selectfield="id,"+insertfield;
    @Insert({"INSERT INTO ",tablename,"("+insertfield+") Values(#{name},#{password},#{salt},#{head_url},#{activation},#{code})"})
    int insertuser(User user);

    @Select({"Select ",selectfield," from ",tablename," where id=#{id}"})
    User selectuser(int id);

    @Select({"Select ",selectfield," from ",tablename," where name=#{name}"})
    User selectname(String name);

    @Update({"Update ",tablename," set password=#{password} where id=#{id}"})
    void updateuser(User user);

    @Update({"Update ",tablename," set activation=1 where id=#{id}"})
    void updateActivation(int id);

    @Delete({"Delete from ",tablename," where id=#{id}"})
    void deleteuser(int id);
}
