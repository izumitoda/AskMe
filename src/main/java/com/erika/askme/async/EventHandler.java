package com.erika.askme.async;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 11:11
 **/
public interface EventHandler {
    void doHandle(EventModel event);//执行Event的具体操作函数
    List<EventType> getSupportEventType();//返回该handler对那些类型的Event是关心的，即这些EventType进入队列需要运行时，需要调用该Handler
}
