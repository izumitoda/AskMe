package com.erika.askme.model;

import org.springframework.stereotype.Component;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-12 22:09
 **/
@Component
public class HostHolder {
    private static ThreadLocal<User> user=new ThreadLocal<User>();

    public User getuser()
    {
        return user.get();
    }
    public void setuser(User u)
    {
        user.set(u);
    }
    public void clear()
    {
        user.remove();
    }
}
