package com.erika.askme.async;

/**
 * @program: askme
 * @description:
 * @author: Erika
 * @create: 2018-02-20 10:46
 **/
public enum EventType {
    LIKE(0),
    COMMENT(1),
    LOGIN(2),
    ASK(3),
    Mail(4),
    Follow(5),
    UnFollow(6),
    Register(7);

    private int value;
    EventType(int value)
    {
        this.value=value;
    }
    public int getValue()
    {
        return this.value;
    }

}
