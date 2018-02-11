package com.erika.askme.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 22:53
 **/
@Controller
public class HomeController {

    @RequestMapping(path={"/","/index"})
    public static String homepage()
    {
        return "index";
    }
}
