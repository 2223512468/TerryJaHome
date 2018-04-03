package com.jajahome.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 */
public class YunOrderModel {


    /**
     * data : {"Waybill":[{"waybill_number":"1202516375387","order_list":[{"desc":",实木,,\n价格: ¥1000","number":1,"name":"板式床","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]}]}
     * cmd : order_Waybill_query
     * status : 0
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<WaybillBean> Waybill;

        public List<WaybillBean> getWaybill() {
            return Waybill;
        }

        public void setWaybill(List<WaybillBean> Waybill) {
            this.Waybill = Waybill;
        }

        public static class WaybillBean {
            /**
             * waybill_number : 1202516375387
             * order_list : [{"desc":",实木,,\n价格: ¥1000","number":1,"name":"板式床","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]
             */

            private String waybill_number;
            private List<OrderListBean> order_list;

            public String getWaybill_number() {
                return waybill_number;
            }

            public void setWaybill_number(String waybill_number) {
                this.waybill_number = waybill_number;
            }

            public List<OrderListBean> getOrder_list() {
                return order_list;
            }

            public void setOrder_list(List<OrderListBean> order_list) {
                this.order_list = order_list;
            }

            public static class OrderListBean {
                /**
                 * desc : ,实木,,
                 价格: ¥1000
                 * number : 1
                 * name : 板式床
                 * image : {"small":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}
                 */

                private String desc;
                private int number;
                private String name;
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
                    /**
                     * small : http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg
                     * thumb : http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg
                     * url : http://www.jajahome.com/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg
                     */

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
