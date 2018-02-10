package com.erika.askme.model;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-10 15:48
 **/
public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public User(String name)
    {
        this.name=name;
    }
    public String getDescription()
    {
        return "This is my description";
    }
    public String getdescription()
    {
        return "This is my ddescription";
    }
}
