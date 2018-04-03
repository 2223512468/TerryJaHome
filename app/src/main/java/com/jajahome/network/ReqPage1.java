package com.jajahome.network;

import com.jajahome.constant.Constant;
import com.jajahome.model.ConfigModel;

/**
 * Created by lhz on 2016/7/19.
 */
public class ReqPage1 {

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

    public static class ContentBean {
        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        private PaginationBean pagination;


        private int[] idArray;

        public int[] getIdArray() {
            return idArray;
        }

        public void setIdArray(int[] idArray) {
            this.idArray = idArray;
        }

        private String id;
        //  order_id | 数值 | 订单编号
        //price_type | 数值 | 价格类型
        private String order_id;

        private Float order_price;

        private int pay_count;

        public int getPay_count() {
            return pay_count;
        }

        public void setPay_count(int pay_count) {
            this.pay_count = pay_count;
        }

        public Float getOrder_price() {
            return order_price;
        }

        public void setOrder_price(Float order_price) {
            this.order_price = order_price;
        }

        public Integer getPrice_type() {
            return price_type;
        }

        public void setPrice_type(Integer price_type) {
            this.price_type = price_type;
        }

        public String getOrder_id() {
            return order_id;
        }

        public void setOrder_id(String order_id) {
            this.order_id = order_id;
        }

        private Integer price_type;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        private String city;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        private Integer status;
        private String id_type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getId_type() {
            return id_type;
        }

        public void setId_type(String id_type) {
            this.id_type = id_type;
        }

        private String type;

        public FilterBean getFilter() {
            return filter;
        }

        public void setFilter(FilterBean filter) {
            this.filter = filter;
        }

        private FilterBean filter;

        public static class PaginationBean {
            private int offset;

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            private int limit = Constant.PAGE_LIMIT;
        }

        public static class FilterBean {
            //			room | 数值 | 空间
//			category | 数值 | 类别
//			brand | 数值 | 类别
//			style | 数值 | 风格
//			user | 数值 | 用户类型
//			type | 数值 | 类型
//			time | 数值 | 时间类型
//			color | 数值 | 色系
//			price | `PRICE_RANGE` | 价格
//			order_by | 数值 | 排序方式
            private Integer room;
            private Integer category;
            private Integer brand;
            private Integer style;
            private Integer color;
            private Integer user;
            private Integer type;
            private Integer time;

            public Integer getCity() {
                return city;
            }

            public void setCity(Integer city) {
                this.city = city;
            }

            private Integer city;

            public Integer getTime() {
                return time;
            }

            public void setTime(Integer time) {
                this.time = time;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            private ConfigModel.DataBean.ConfigBean.ItemPriceBean price;

            public ConfigModel.DataBean.ConfigBean.ItemPriceBean getPrice() {
                return price;
            }

            public void setPrice(ConfigModel.DataBean.ConfigBean.ItemPriceBean price) {
                this.price = price;
            }

            public void setSetPrice(ConfigModel.DataBean.ConfigBean.SetPriceBean bean) {
                ConfigModel.DataBean.ConfigBean.ItemPriceBean price = new ConfigModel.DataBean.ConfigBean.ItemPriceBean();
                price.setId(bean.getId());
                price.setMin(bean.getMin());
                price.setMax(bean.getMax());
                this.price = price;
            }

            public Integer getColor() {
                return color;
            }

            public void setColor(Integer color) {
                this.color = color;
            }

            public int getRoom() {
                return room;
            }

            public void setRoom(int room) {
                this.room = room;
            }

            public int getCategory() {
                return category;
            }

            public void setCategory(int category) {
                this.category = category;
            }

            public int getBrand() {
                return brand;
            }

            public void setBrand(int brand) {
                this.brand = brand;
            }

            public int getStyle() {
                return style;
            }

            public void setStyle(int style) {
                this.style = style;
            }

            public int getUser() {
                return user;
            }

            public void setUser(int user) {
                this.user = user;
            }
        }
    }


}
