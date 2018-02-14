package com.erika.askme.service;

import com.erika.askme.dao.messagedao;
import com.erika.askme.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 13:54
 **/
public class MessageService {
    @Autowired
    messagedao messagedata;
    @Autowired
    SensitiveService sensitive;
    public int insertMessage(Message msg)
    {
        msg.setContent(HtmlUtils.htmlEscape(msg.getContent()));
        //敏感词过滤
        msg.setContent(sensitive.filterword(msg.getContent()));
        return messagedata.insertmessage(msg);
    }
    public List<Message> getUnreadMessage(int myid)
    {
        return messagedata.getUnreadMessage(myid);
    }
    public List<Message> getAllMessageRecieved(int myid)
    {
        return messagedata.getMessage(myid);
    }
    public List<Message> getAllMessageSend(int myid)
    {
        return messagedata.sendMessage(myid);
    }
    public void readMessageUpdate(int messageid)
    {
        messagedata.readMessage(messageid);
    }
    public List<Message> getConversation(int conversationid)
    {
        return messagedata.getConversation(conversationid);
    }

}
