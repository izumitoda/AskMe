package com.erika.askme.controller;

import com.erika.askme.model.User;
import com.erika.askme.service.newservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @program: askme
 * @description: the home page
 * @author: Erika
 * @create: 2018-02-10 13:09
 **/
@Controller
public class IndexController {

    @Autowired
    newservice myservice;

    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello AskMe"+myservice.getmsg();
    }

    @RequestMapping(path = {"/profile/{groupname}/{userName}"})
    @ResponseBody
    public String profileuserID(@PathVariable("userName") String username,
                                @PathVariable("groupname") String groupname,
                                @RequestParam(value = "type",defaultValue = "1",required=false) int type,
                                @RequestParam(value = "id",defaultValue="10",required = false) Integer id) {
        return String.format("Profile page of %s from %s \n Request for type %d of %d",username,groupname,type,id);
    }

    @RequestMapping(path={"/template"},method = {RequestMethod.GET})
    public String template(Model model)
    {
        model.addAttribute("name","Erika");
        List<String> list= Arrays.asList(new String[]{"oasis","blur","the libertines"});
        model.addAttribute("band",list);
        Map<String,String> map=new HashMap<String,String>();
        for(int i=0;i<3;i++)
            map.put(String.valueOf(i+1),list.get(i));
        model.addAttribute("map",map);
        model.addAttribute("user",new User("Erika"));
        return "home";
    }

    @RequestMapping(path={"/request"})
    @ResponseBody
    public String response(HttpServletRequest request, HttpServletResponse response)
    { response.addCookie(new Cookie("hello","you_are_my_cookies"));
        response.addHeader("hello","jkdsalk");
        StringBuilder ss=new StringBuilder();
        ss.append(request.getMethod()+"<br>");
        ss.append(request.getPathInfo()+"<br>");
        ss.append(request.getCookies()+"<br>");
        Enumeration<String> req=request.getHeaderNames();
        while(req.hasMoreElements()){
            String name=req.nextElement();
            ss.append(name+":"+request.getHeader(name)+"<br>");
        }

        ss.append(request.getQueryString());

        ss.append("<br>"+response.getStatus()+"<br>");

        return ss.toString();
    }

    @RequestMapping(path="/redirect/{code}")
    public RedirectView redirect(@PathVariable("code") int code)
    {
        RedirectView red=new RedirectView("/",true);
        if(code==222)
            red.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        return red;
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e)
    {
        return "error "+e.getMessage();
    }

    @RequestMapping(path={"/testerror/{id}"})
    @ResponseBody
    public String testerror(@PathVariable("id") int id)
    {
        if(id>100)
        {
            throw new IllegalArgumentException("访问失效");
        }
        else
            return "you are right";
    }
}
