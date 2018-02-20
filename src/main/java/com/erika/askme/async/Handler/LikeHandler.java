package com.erika.askme.async.Handler;

import com.erika.askme.async.EventModel;
import com.erika.askme.async.EventType;
import com.erika.askme.model.Message;
import com.erika.askme.model.User;
import com.erika.askme.service.MessageService;
import com.erika.askme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import com.erika.askme.async.EventHandler;
import org.springframework.stereotype.Component;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 13:21
 **/
@Component
public class LikeHandler implements EventHandler {
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
        msg.setContent("用户"+user.getName()+"赞了您的评论 "+event.getkeyvalue("questionid"));
        msg.setHasread(0);
        msg.setConversationid(event.getEntityownerid(),888);
        messageService.insertMessage(msg);
    }

    @Override
    public List<EventType> getSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
