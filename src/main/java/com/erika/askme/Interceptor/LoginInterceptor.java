package com.erika.askme.Interceptor;

import com.erika.askme.model.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-13 09:34
 **/
@Component
public class LoginInterceptor implements HandlerInterceptor{
    @Autowired
    HostHolder host;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
     if(host.getuser()==null)
     {
        httpServletResponse.sendRedirect("/reglogin?next="+httpServletRequest.getRequestURI());
     }
     return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
