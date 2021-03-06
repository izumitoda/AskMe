package com.erika.askme.model;

import org.springframework.stereotype.Component;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-10 15:48
 **/
@Component
public class User {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;
    private String name;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String salt;

    public String getHeadUrl() {
        return head_url;
    }

    public void setHeadUrl(String headUrl) {
        this.head_url = headUrl;
    }

    private String head_url;
    public User(){}
    public User(String name)
    {
        this.name=name;
    }
    public User(String name,String password)
    {
        this.name=name;
        this.password=password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getActivation() {
        return activation;
    }

    public void setActivation(int activation) {
        this.activation = activation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private int activation;
    private String code;

}
