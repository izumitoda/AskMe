package com.erika.askme.async.Handler;

import com.erika.askme.async.EventHandler;
import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventType;
import com.erika.askme.service.UserService;
import com.erika.askme.utils.SendMails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 21:53
 **/
@Component
public class LoginExceptionHandler implements EventHandler{

    @Autowired
    SendMails sendMails;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel event) {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("username",userService.getuserbyid(event.getUserid()).getName());
        sendMails.sendWithHTMLTemplate(event.getkeyvalue("mail"),"用户登录异常","/src/main/resources/templates/mails/LoginException.html",map);

    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LOGIN);
    }
}
