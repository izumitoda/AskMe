package com.erika.askme.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-11 23:59
 **/
public class ViewObject{
    private Map<String,Object> obj=new HashMap<String,Object>();
    public void set(String key,Object value)
    {
        obj.put(key,value);
    }
    public Object get(String key)
    {
        return obj.get(key);
    }
}
