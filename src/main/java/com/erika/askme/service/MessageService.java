package com.erika.askme.service;

import com.erika.askme.dao.messagedao;
import com.erika.askme.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-14 13:54
 **/
@Service
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
    public List<Message> getConversation(String conversationid)
    {
        return messagedata.getConversation(conversationid);
    }
    public  List<Message> getListMessage(int id)
    {
        return messagedata.listmessageget(id);
    }
    public int getAllCountByConversationId(String conversation_id)
    {
        return messagedata.getAllCountByConversationId(conversation_id);
    }
    public int getAllUnreadCountByConversationId(String conversation_id, int myid)
    {
        return messagedata.getAllUnreadCountByConversationId(conversation_id,myid);
    }

}
