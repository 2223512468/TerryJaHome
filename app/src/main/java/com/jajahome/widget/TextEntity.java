package com.jajahome.widget;

/**
 * Created by lhz on 2016/1/22.
 */
public class TextEntity {

    public String text;
    public String desc;

    public TextEntity(String text, String desc) {
        this.text = text;
        this.desc = desc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
