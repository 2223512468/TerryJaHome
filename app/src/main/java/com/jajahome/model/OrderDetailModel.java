package com.jajahome.model;

import java.util.List;

/**
 * 订单详情model
 */
public class OrderDetailModel {

    /**
     * order : {"order_id":"733020531472091338","price":"900.00","status":"0","paymode":[1,2],"adress":{"detail_address":"北京市 红魔","name":"红魔","mobile":"15036057330","tel":"","postcode":""},"order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}],"sales_service":0}
     */

    private DataBean data;
    /**
     * data : {"order":{"order_id":"733020531472091338","price":"900.00","status":"0","paymode":[1,2],"adress":{"detail_address":"北京市 红魔","name":"红魔","mobile":"15036057330","tel":"","postcode":""},"order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}],"sales_service":0}}
     * cmd : order
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
         * order_id : 733020531472091338
         * price : 900.00
         * status : 0
         * paymode : [1,2]
         * adress : {"detail_address":"北京市 红魔","name":"红魔","mobile":"15036057330","tel":"","postcode":""}
         * order_list : [{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]
         * sales_service : 0
         */

        private OrderBean order;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public static class OrderBean {
            private String order_id;
            private String price;
            /**
             * 0 - 待处理 ,
             * 1 - 已处理(用户付款成功后,通过此表生成对应物流表中数据) ,
             * 2 - 订单取消(交易关闭)
             * 3 交易完成 ,
             * 4 退款中
             * 5 退款成功(交易关闭)
             * 6 请询价(必须客服改价后才能处理)
             * 7 已发货(待收货)
             */

            private String status;
            /**
             * detail_address : 北京市 红魔
             * name : 红魔
             * mobile : 15036057330
             * tel :
             * postcode :
             */

            private AdressBean adress;
            private int sales_service;
            private List<Integer> paymode;
            /**
             * desc : 实木
             价格: ¥900
             * number : 1
             * name : 板式床
             * image : {"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}
             */

            private List<OrderListBean> order_list;

            public String getOrder_id() {
                return order_id;
            }

            public void setOrder_id(String order_id) {
                this.order_id = order_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public AdressBean getAdress() {
                return adress;
            }

            public void setAdress(AdressBean adress) {
                this.adress = adress;
            }

            public int getSales_service() {
                return sales_service;
            }

            public void setSales_service(int sales_service) {
                this.sales_service = sales_service;
            }

            public List<Integer> getPaymode() {
                return paymode;
            }

            public void setPaymode(List<Integer> paymode) {
                this.paymode = paymode;
            }

            public List<OrderListBean> getOrder_list() {
                return order_list;
            }

            public void setOrder_list(List<OrderListBean> order_list) {
                this.order_list = order_list;
            }

            public static class AdressBean {
                private String detail_address;
                private String name;
                private String mobile;
                private String tel;
                private String postcode;

                public String getDetail_address() {
                    return detail_address;
                }

                public void setDetail_address(String detail_address) {
                    this.detail_address = detail_address;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getMobile() {
                    return mobile;
                }

                public void setMobile(String mobile) {
                    this.mobile = mobile;
                }

                public String getTel() {
                    return tel;
                }

                public void setTel(String tel) {
                    this.tel = tel;
                }

                public String getPostcode() {
                    return postcode;
                }

                public void setPostcode(String postcode) {
                    this.postcode = postcode;
                }
            }

            public static class OrderListBean {
                private String desc;
                private int number;
                private String name;
                /**
                 * small : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg
                 * thumb : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg
                 * url : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg
                 */

                private ImageBean image;

                public String getDesc() {
                    return desc;
                }

                public void setDesc(String desc) {
                    this.desc = desc;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public ImageBean getImage() {
                    return image;
                }

                public void setImage(ImageBean image) {
                    this.image = image;
                }

                public static class ImageBean {
                    private String small;
                    private String thumb;
                    private String url;

                    public String getSmall() {
                        return small;
                    }

                    public void setSmall(String small) {
                        this.small = small;
                    }

                    public String getThumb() {
                        return thumb;
                    }

                    public void setThumb(String thumb) {
                        this.thumb = thumb;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }
}
