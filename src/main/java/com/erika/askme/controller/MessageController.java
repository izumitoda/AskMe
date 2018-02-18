package com.erika.askme.controller;

import com.erika.askme.dao.messagedao;
import com.erika.askme.model.*;
import com.erika.askme.service.MessageService;
import com.erika.askme.service.UserService;
import com.erika.askme.utils.WendaUtil;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.swing.text.View;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 14:02
 **/
@Controller
public class MessageController {
    private static final Logger logger= LoggerFactory.getLogger(MessageController.class) ;
    @Autowired
    MessageService messageservice;
    @Autowired
    HostHolder host;
    @Autowired
    UserService userservice;

    @RequestMapping(path={"/msg/list"},method=RequestMethod.GET)
    public String getMyMessage(Model model)
    {
        if(host.getuser()==null)
            return "redirect:/reglogin?next=/msg/list";
        User user=host.getuser();
        model.addAttribute("me",user);
        int myid=user.getId();
        List<Message> msg=messageservice.getListMessage(myid);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Message a:msg)
        {
            ViewObject vo=new ViewObject();
            vo.set("msg",a);
            User from=userservice.getuserbyid(a.getFromid());
            if(from.getId()==user.getId())
                vo.set("another",userservice.getuserbyid(a.getToid()));
            else
                vo.set("another",userservice.getuserbyid(a.getFromid()));
            vo.set("unreadcount",messageservice.getAllUnreadCountByConversationId(a.getConversationid(),user.getId()));
            vo.set("readcount",messageservice.getAllCountByConversationId(a.getConversationid()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        model.addAttribute("me",user);

        return "letter";
    }
    @RequestMapping(path={"/msg/conversation"},method={RequestMethod.GET})
    public String getMyMessage(Model model,@RequestParam(value="conversation",required = false)String conversation_id)
    {
        if(host.getuser()==null)
            return "/reglogin?next="+ "/msg/conversation";
        User user=host.getuser();
        User from;
        String[] fromto=conversation_id.split("_");
        if(fromto.length!=2)
            return "redirect:/";
        if(fromto[0].equals(String.valueOf(user.getId())))
        {
            from=userservice.getuserbyid(Integer.parseInt(fromto[1]));
        }
        else
        {
            if(fromto[1].equals(String.valueOf(user.getId())))
                from=userservice.getuserbyid(Integer.parseInt(fromto[0]));
            else
                return "redirect:/";
        }
        if(from==null)
            return "redirect:/";
        model.addAttribute("from",from);
        List<Message> msg=messageservice.getConversation(conversation_id);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Message a:msg)
        {
            ViewObject vo=new ViewObject();
            vo.set("msg",a);
            vo.set("from",userservice.getuserbyid(a.getFromid()));
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        return "letterDetail";
    }

    @RequestMapping(path={"/addMessage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("content") String msg,@RequestParam("toName") String name)
    {
        try
        {
            Message message=new Message();
            User user=userservice.getUserByName(name);
            if(host.getuser()==null)
                return WendaUtil.generatejson(999,"请登录");
            if(user!=null)
                message.setToid(userservice.getUserByName(name).getId());
            else {
                String tmp = WendaUtil.generatejson(1, "fdafdsaf");
                System.out.println(tmp);
                return WendaUtil.generatejson(1, "该用户不存在");
            }
                message.setFromid(host.getuser().getId());
            message.setHasread(0);
            message.setContent(msg);

            message.setConversationid(message.getFromid(),message.getToid());
            message.setCreateddate(new Date());
            messageservice.insertMessage(message);
            return WendaUtil.generatejson(0);
        }
        catch(Exception e){
            logger.error("发送私信失败"+e.getMessage());
            return WendaUtil.generatejson(1,"发送失败");
        }
    }
}
