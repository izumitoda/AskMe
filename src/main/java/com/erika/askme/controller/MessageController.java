package com.erika.askme.controller;

import com.erika.askme.dao.messagedao;
import com.erika.askme.model.HostHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

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
    messagedao message;
    @Autowired
    HostHolder host;


}
