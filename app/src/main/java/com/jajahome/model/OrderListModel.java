package com.jajahome.model;

import java.util.List;

/**
 * 订单列表 数据
 */
public class OrderListModel {

    /**
     * list : [{"order_id":"733020531472178657","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472177036","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472176102","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472176071","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472172177","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472120075","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472119034","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472118656","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117694","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117646","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":"1","name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117283","price":"900.00","status":"1","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472115975","price":"900.00","status":"1","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]}]
     * pagination : {"total":"47","count":4,"limit":12,"offset":1}
     */

    private DataBean data;
    /**
     * data : {"list":[{"order_id":"733020531472178657","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472177036","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472176102","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472176071","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472172177","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472120075","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472119034","price":"900.00","status":"0","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472118656","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117694","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":1,"name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117646","price":"10800.00","status":"0","order_list":[{"desc":"棉\n价格: ¥10800","number":"1","name":"软包床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/bcb/49b/afa/453/18721e47353e7db4cfdc.jpg"}}]},{"order_id":"733020531472117283","price":"900.00","status":"1","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]},{"order_id":"733020531472115975","price":"900.00","status":"1","order_list":[{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]}],"pagination":{"total":"47","count":4,"limit":12,"offset":1}}
     * cmd : order_list
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
         * total : 47
         * count : 4
         * limit : 12
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * order_id : 733020531472178657
         * price : 900.00
         * status : 0
         * order_list : [{"desc":"实木\n价格: ¥900","number":1,"name":"板式床","image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}}]
         */

        private List<ListBean> list;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class PaginationBean {
            private String total;
            private int count;
            private int limit;
            private int offset;

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public int getLimit() {
                return limit;
            }

            public void setLimit(int limit) {
                this.limit = limit;
            }

            public int getOffset() {
                return offset;
            }

            public void setOffset(int offset) {
                this.offset = offset;
            }
        }

        public static class ListBean {
            private String order_id;
            private String price;
            private String status;
            /**
             * desc : 实木
             价格: ¥900
             * number : 1
             * name : 板式床
             * image : {"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}
             */

            private List<OrderDetailModel.DataBean.OrderBean.OrderListBean> order_list;

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

            public List<OrderDetailModel.DataBean.OrderBean.OrderListBean> getOrder_list() {
                return order_list;
            }

            public void setOrder_list(List<OrderDetailModel.DataBean.OrderBean.OrderListBean> order_list) {
                this.order_list = order_list;
            }


        }
    }
}
