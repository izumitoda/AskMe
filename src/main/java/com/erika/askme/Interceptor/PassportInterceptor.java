package com.erika.askme.Interceptor;

import com.erika.askme.dao.ticketdao;
import com.erika.askme.dao.userdao;
import com.erika.askme.model.HostHolder;
import com.erika.askme.model.LoginTicket;
import com.erika.askme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-12 20:28
 **/
@Component
public class PassportInterceptor implements HandlerInterceptor {
    @Autowired
    ticketdao ticketdata;
    @Autowired
    userdao userdata;
    @Autowired
    HostHolder host;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String ticket=null;
        if(httpServletRequest.getCookies()!=null)
        {
            for(Cookie cookie:httpServletRequest.getCookies())
            {
                if(cookie.getName().equals("ticket"))
                {
                    ticket=cookie.getValue();
                    break;
                }
            }
        }

        if(ticket!=null)
        {
            LoginTicket lgticket=ticketdata.selectticket(ticket);
            //如果ticket不在数据库中，或者ticket已经过期了，或者ticket的status不为0，说明失效，就返回true
            if(lgticket==null || lgticket.getExpired().before(new Date()) || lgticket.getStatus()!=0)
            {
                return true;
            }
            //否则的话就根据ticket来获取用户信息
            User user=userdata.selectuser(lgticket.getUserid());
            host.setuser(user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
            if(modelAndView!=null)
            {
                modelAndView.addObject("user",host.getuser());
            }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        host.clear();
    }
}
