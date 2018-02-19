package com.erika.askme.controller;

import com.erika.askme.model.EntityType;
import com.erika.askme.model.HostHolder;
import com.erika.askme.model.User;
import com.erika.askme.service.LikeService;
import com.erika.askme.utils.WendaUtil;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-19 10:16
 **/
@Controller
public class LikeController {
    @Autowired
    HostHolder host;

    @Autowired
    LikeService like;

    @RequestMapping(path={"/like"},method={RequestMethod.POST})
    @ResponseBody
    public String likeit(@RequestParam("commentId")int entity_id)
    {
        User user=host.getuser();
        if(user==null)
            return WendaUtil.generatejson(999);
        if(like.isLikeorDislike(EntityType.ENTITY_COMMENT,entity_id,user.getId())==2)
        {
            like.delDislike(EntityType.ENTITY_COMMENT,entity_id,user.getId());
        }
        like.addLike(EntityType.ENTITY_COMMENT,entity_id,user.getId());
        return WendaUtil.generatejson(0,String.valueOf(like.getCountLike(EntityType.ENTITY_COMMENT,entity_id)));
    }
    @RequestMapping(path={"/dislike"},method={RequestMethod.POST})
    @ResponseBody
    public String dislikeit(@RequestParam("commentId")int entity_id)
    {
        User user=host.getuser();
        if(user==null)
            return WendaUtil.generatejson(999);
        if(like.isLikeorDislike(EntityType.ENTITY_COMMENT,entity_id,user.getId())==1)
        {
            like.dellike(EntityType.ENTITY_COMMENT,entity_id,user.getId());
        }
        like.addDislike(EntityType.ENTITY_COMMENT,entity_id,user.getId());
        return WendaUtil.generatejson(0,String.valueOf(like.getCountLike(EntityType.ENTITY_COMMENT,entity_id)));
    }
}
