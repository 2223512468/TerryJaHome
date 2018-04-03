package com.jajahome.network;

import com.jajahome.model.DiyPosModel;

import java.util.List;

/**
 * 保存diy 网络请求体
 */
public class DiyReq {


    /**
     * cmd : home
     * content : {"id":1}
     */

    private String cmd;
    /**
     * id : 1
     */

    private ContentBean content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }
//    id | 数值 | mydiy 编号
//    name | 字符串 | 名字
//    desc | 字符串 | 描述
//    diy_id | 数值 | diy 模板编号
//    diy_pos | `DIY_POS`数组 | diy 位置信息
    public static class ContentBean {
        private String id;
        private String name;
        private String desc;
        private String diy_id;
        private List<DiyPosModel> diy_pos;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDiy_id() {
        return diy_id;
    }

    public void setDiy_id(String diy_id) {
        this.diy_id = diy_id;
    }

    public List<DiyPosModel> getDiy_pos() {
        return diy_pos;
    }

    public void setDiy_pos(List<DiyPosModel> diy_pos) {
        this.diy_pos = diy_pos;
    }
}
}
