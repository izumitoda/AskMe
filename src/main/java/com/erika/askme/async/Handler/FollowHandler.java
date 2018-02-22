package com.erika.askme.async.Handler;

import com.erika.askme.async.EventHandler;
import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventType;
import com.erika.askme.model.EntityType;
import com.erika.askme.model.Message;
import com.erika.askme.model.User;
import com.erika.askme.service.MessageService;
import com.erika.askme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 13:50
 **/
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    //给被点赞用户发送站内信
    @Override
    public void doHandle(EventModel event) {
        Message msg=new Message();
        msg.setToid(event.getEntityownerid());
        msg.setCreateddate(new Date());
        msg.setFromid(888);
        User user=userService.getuserbyid(event.getUserid());
        String content=getMessageContent(event);
        if(content==null)
            return;
        msg.setContent(content);
        msg.setHasread(0);
        msg.setConversationid(event.getEntityownerid(),888);
        messageService.insertMessage(msg);
    }

    public String getMessageContent(EventModel event)
    {
        User user=userService.getuserbyid(event.getUserid());
        int type=event.getEntitytype();
        int entity_id=event.getEntityid();
        StringBuilder str=new StringBuilder();
        str.append("用户"+user.getName()+"关注了您");
        if(event.getEventype()==EventType.Follow)
        {
            if(type== EntityType.ENTITY_USER)
                return str.toString();
            else
            {
                str.append("的问题 "+"http://127.0.0.1:8080/question/"+String.valueOf(entity_id));
                return str.toString();
            }
        }
        return null;
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.Follow,EventType.UnFollow);
    }
}