package com.erika.askme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: askme
 * @description: the home page
 * @author: Erika
 * @create: 2018-02-10 13:09
 **/
@Controller
public class IndexController {
    @RequestMapping(path = {"/", "/index"})
    @ResponseBody
    public String index() {
        return "Hello AskMe";
    }

    @RequestMapping(path = {"/profile/{groupname}/{userName}"})
    @ResponseBody
    public String profileuserID(@PathVariable("userName") String username,
                                @PathVariable("groupname") String groupname,
                                @RequestParam(value = "type",defaultValue = "1",required=false) int type,
                                @RequestParam(value = "id",defaultValue="10",required = false) Integer id) {
        return String.format("Profile page of %s from %s \n Request for type %d of %d",username,groupname,type,id);
    }

}
