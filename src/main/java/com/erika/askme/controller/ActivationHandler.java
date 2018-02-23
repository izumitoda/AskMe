package com.erika.askme.controller;

import com.erika.askme.model.User;
import com.erika.askme.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-23 22:55
 **/
@Controller
public class ActivationHandler {
    @Autowired
    UserService userService;

    @RequestMapping(path={"/activation/{code}/{userid}"},method = {RequestMethod.GET})
    public String activataionYourAccount(RedirectAttributes redirectAttributes,@PathVariable("code")String code, @PathVariable("userid")int userid, Model model)
    {
        User user= userService.getuserbyid(userid);

        if(user.getCode().equals(code))
        {
            userService.updateActivation(userid);
            redirectAttributes.addFlashAttribute("msg","激活成功，请重新登录");
            return "redirect:/reglogin";
        }
        redirectAttributes.addFlashAttribute("msg","错误的激活链接");
        return "redirect:/reglogin";
    }
}
