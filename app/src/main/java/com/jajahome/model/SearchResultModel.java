package com.jajahome.model;

import java.util.List;

/**
 * Created by liuhaizhu on 2017/8/4.
 */

public class SearchResultModel {

    /**
     * data : {"pagination":{"total":"3","count":1,"limit":100,"offset":1},"item":[{"id":"431","time":0,"title":"比佛利沙发桌","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg"},"url":"","action":"item","action_id":"431","price":"7380.00","price_basic":{"id":431,"min":7380,"max":7380},"price_discount":{"id":431,"min":7380,"max":7380}},{"id":"995","time":0,"title":"测试单品11","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg"},"url":"","action":"item","action_id":"995","price":"2000.00","price_basic":{"id":995,"min":2000,"max":2000},"price_discount":{"id":995,"min":2000,"max":2000}},{"id":"1957","time":0,"title":" 罗马柱式长型沙发桌","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265.png"},"url":"","action":"item","action_id":"1957","price":"0.00","price_basic":{"id":1957,"min":0,"max":0},"price_discount":{"id":1957,"min":0,"max":0}}]}
     * cmd : search
     * status : 0
     */

    private DataBean data;
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
         * pagination : {"total":"3","count":1,"limit":100,"offset":1}
         * item : [{"id":"431","time":0,"title":"比佛利沙发桌","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg"},"url":"","action":"item","action_id":"431","price":"7380.00","price_basic":{"id":431,"min":7380,"max":7380},"price_discount":{"id":431,"min":7380,"max":7380}},{"id":"995","time":0,"title":"测试单品11","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/c8a/081/fd3/b8a/d4b4f69c5bcbaf024285.jpg"},"url":"","action":"item","action_id":"995","price":"2000.00","price_basic":{"id":995,"min":2000,"max":2000},"price_discount":{"id":995,"min":2000,"max":2000}},{"id":"1957","time":0,"title":" 罗马柱式长型沙发桌","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/31f/f5c/5ba/78e/1c84bea4307cc97cb265.png"},"url":"","action":"item","action_id":"1957","price":"0.00","price_basic":{"id":1957,"min":0,"max":0},"price_discount":{"id":1957,"min":0,"max":0}}]
         */

        private PaginationBean pagination;
        private List<ItemBean> item;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class PaginationBean {
            /**
             * total : 3
             * count : 1
             * limit : 100
             * offset : 1
             */

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

        public static class ItemBean {
            /**
             * id : 431
             * time : 0
             * title : 比佛利沙发桌
             * image : {"small":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg"}
             * url :
             * action : item
             * action_id : 431
             * price : 7380.00
             * price_basic : {"id":431,"min":7380,"max":7380}
             * price_discount : {"id":431,"min":7380,"max":7380}
             */

            private String id;
            private int time;
            private String title;
            private ImageBean image;
            private String url;
            private String action;
            private String action_id;
            private String price;
            private PriceBasicBean price_basic;
            private PriceDiscountBean price_discount;
            private String category;
            private String tips;
            private int count;
            private String type;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }


            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public String getTips() {
                return tips;
            }

            public void setTips(String tips) {
                this.tips = tips;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getAction_id() {
                return action_id;
            }

            public void setAction_id(String action_id) {
                this.action_id = action_id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public PriceBasicBean getPrice_basic() {
                return price_basic;
            }

            public void setPrice_basic(PriceBasicBean price_basic) {
                this.price_basic = price_basic;
            }

            public PriceDiscountBean getPrice_discount() {
                return price_discount;
            }

            public void setPrice_discount(PriceDiscountBean price_discount) {
                this.price_discount = price_discount;
            }

            public static class ImageBean {
                /**
                 * small : http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg
                 * thumb : http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg
                 * url : http://www.jajahome.com/gjj/backend/web/images/table/942_135.jpg
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

            /*public static class PriceBasicBean {
                *//**
             * id : 431
             * min : 7380
             * max : 7380
             *//*

                private int id;
                private int min;
                private int max;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }
            }*/

           /* public static class PriceDiscountBean {
                *//**
             * id : 431
             * min : 7380
             * max : 7380
             *//*

                private int id;
                private int min;
                private int max;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public int getMin() {
                    return min;
                }

                public void setMin(int min) {
                    this.min = min;
                }

                public int getMax() {
                    return max;
                }

                public void setMax(int max) {
                    this.max = max;
                }
            }*/
        }
    }
}
