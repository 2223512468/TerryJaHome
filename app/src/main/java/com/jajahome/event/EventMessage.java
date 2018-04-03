package com.jajahome.event;

/**
 * 类描述：EventBus消息实体
 * 创建人：lhz
 * 创建时间：2016/6/29 13:46
 * 修改时间：2016/6/29 13:46
 * 修改备注：
 */public class EventMessage {
    public int action;
    public String msg;

    public EventMessage(int action, String msg){
        this.action=action;
        this.msg=msg;
    }
}

