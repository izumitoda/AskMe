package com.erika.askme.service;

import com.erika.askme.dao.ticketdao;
import com.erika.askme.dao.userdao;
import com.erika.askme.model.LoginTicket;
import com.erika.askme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;

import static com.erika.askme.utils.WendaUtil.MD5;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 23:48
 **/
@Service
public class UserService {
    @Autowired
    private userdao userdate;
    @Autowired
    private ticketdao ticketdata;
    public User getuserbyid(int id)
    {
        return userdate.selectuser(id);
    }
    public  Map<String,String> login(String name,String password)
    {
        Map<String,String> map=new HashMap<String, String>();
        if(StringUtils.isEmpty(name))
        {
            map.put("msg","用户名不能为空");

            return map;
        }
        if(StringUtils.isEmpty(password))
        {
            map.put("msg","密码不能为空");

            return map;
        }
        if(userdate.selectname(name)==null)
        {
            map.put("msg","该用户名不存在");
            return map;
        }
        User user=userdate.selectname(name);
        if(!((MD5(password+user.getSalt())).equals(user.getPassword())))
        {
            map.put("msg","密码错误");
            return map;
        }
        map.put("ticket",addticket(user.getId()));
        return map;
    }
    public Map<String,String> register(String name,String password)
    {
        Map<String,String> map=new HashMap<String,String>();
        if(StringUtils.isEmpty(name))
        {
            map.put("msg","用户名不能为空");

            return map;
        }
        if(StringUtils.isEmpty(password))
        {
            map.put("msg","密码不能为空");

            return map;
        }
        if(userdate.selectname(name)!=null)
        {
            map.put("msg","用户名已经被占用");

            return map;
        }
        User user=new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(MD5(password+user.getSalt()));
        userdate.insertuser(user);
        map.put("ticket",addticket(user.getId()));
        return map;
    }

    public String addticket(int user_id)
    {
        LoginTicket lgticket=new LoginTicket();
        lgticket.setUserid(user_id);
        Date date=new Date();
        date.setTime(3600*24*100+date.getTime());
        lgticket.setExpired(date);
        lgticket.setStatus(0);
        lgticket.setTicket(UUID.randomUUID().toString().replaceAll("-",""));
        ticketdata.insertticket(lgticket);
        return lgticket.getTicket();
    }

    public void logout(String ticket)
    {
        ticketdata.updateticket(ticket);
    }
}
