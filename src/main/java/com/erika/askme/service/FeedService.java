package com.erika.askme.service;

import com.erika.askme.dao.feeddao;
import com.erika.askme.model.Feed;
import com.erika.askme.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-22 16:57
 **/
@Service
public class FeedService {
    @Autowired
    feeddao feeddate;

    public int insertIntoFeed(Feed feed)
    {
        return feeddate.insertFeed(feed);
    }

    public Feed getFeedById(int id)
    {
        return feeddate.selectFeedById(id);
    }

    public List<Feed> getFeedsByUserList(int maxId, List<Integer> userIdList,int count)
    {
        return feeddate.selectFeedsByUserList(maxId,userIdList,count);
    }

}
