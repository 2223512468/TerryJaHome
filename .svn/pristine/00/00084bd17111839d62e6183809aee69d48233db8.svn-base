package com.jajahome.model;

import java.util.List;

/**
 * 城市列表 数据
 */
public class CityListModel {

    private DataBean data;
    /**
     * data : {"city":[{"id":330000,"text":"浙江省","items":[{"id":330100,"text":"杭州市","items":[]}]}]}
     * cmd : citylist
     * status : 0
     */

    private String cmd;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * id : 330000
         * text : 浙江省
         * items : [{"id":330100,"text":"杭州市","items":[]}]
         */

        private List<CityBean> city;

        public List<CityBean> getCity() {
            return city;
        }

        public void setCity(List<CityBean> city) {
            this.city = city;
        }

        public static class CityBean {
            private int id;
            private String text;
            /**
             * id : 330100
             * text : 杭州市
             * items : []
             */

            private List<ItemsBean> items;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<ItemsBean> getItems() {
                return items;
            }

            public void setItems(List<ItemsBean> items) {
                this.items = items;
            }

            public static class ItemsBean {
                private int id;
                private String text;
                private List<?> items;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<?> getItems() {
                    return items;
                }

                public void setItems(List<?> items) {
                    this.items = items;
                }
            }
        }
    }
}
