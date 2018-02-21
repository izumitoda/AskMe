package com.erika.askme.controller;

import com.erika.askme.aspect.testaop;
import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventProducer;
import com.erika.askme.async.EventType;
import com.erika.askme.model.EntityType;
import com.erika.askme.service.FollowService;
import com.erika.askme.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-12 14:53
 **/
@Controller
public class LoginController {
    @Autowired
    private UserService userservice;
    private static final Logger logger= LoggerFactory.getLogger(LoginController.class) ;

    @Autowired
    EventProducer eventProducer;

    @Autowired
    FollowService followService;


    @RequestMapping(path={"/register/"},method = {RequestMethod.POST})
    public String register(Model model, @RequestParam("username") String username,@RequestParam("password") String password,HttpServletResponse response,
                           @RequestParam(value="next",required=false) String next)
    {
        try {
            Map<String, String> map = userservice.register(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                if(StringUtils.isEmpty(next));
                else
                {
                    model.addAttribute("next",next);
                }
                return "login";
            } else {
                Cookie cookie=new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if(StringUtils.isEmpty(next));
                else
                {
                    return "redirect:"+next;
                }
                return "redirect:/index";
            }
        }
        catch(Exception e)
        {
            logger.error("注册异常"+e);
            return "login";
        }
    }
    @RequestMapping(path={"/login/"},method = {RequestMethod.POST})
    public String login(Model model, @RequestParam("username") String username, @RequestParam("password") String password,
                        @RequestParam(value="remberme",defaultValue = "false")boolean remember, HttpServletResponse response,
                        @RequestParam(value="next",required=false) String next)
    {
        try {
            Map<String, String> map = userservice.login(username, password);
            if (map.containsKey("msg")) {
                model.addAttribute("msg", map.get("msg"));
                if(StringUtils.isEmpty(next));
                else
                {
                    model.addAttribute("next",next);
                }
                return "login";
            } else {
                Cookie cookie=new Cookie("ticket",map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);

                for(int i=2;i<12;i++)
                {
                    followService.followSomeone(EntityType.ENTITY_USER,i,userservice.getUserByName(username).getId());
                }
                //eventProducer.fireEvent(new EventModel().setkeyvalue("mail","15307130334@fudan.edu.cn").setUserid(userservice.getUserByName(username).getId()).setEventype(EventType.LOGIN));
                if(StringUtils.isEmpty(next));
                else
                {
                    return "redirect:"+next;
                }
                return "redirect:/index";
            }
        }
        catch(Exception e)
        {
            logger.error("登录异常"+e);
            return "login";
        }
    }
    @RequestMapping(path={"/reglogin"},method = {RequestMethod.GET})
    public String reglogin(Model model,@RequestParam(value="next",required=false) String next)
    {
        model.addAttribute("next",next);
        return "login";
    }
    @RequestMapping(path={"/logout/"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket)
    {
        userservice.logout(ticket);
        return "redirect:/";
    }
}
