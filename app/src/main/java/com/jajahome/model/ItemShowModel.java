package com.jajahome.model;

import java.util.List;

/**
 *  单品
 */
public class ItemShowModel {

    /**
     * pagination : {"total":"1","count":1,"limit":100,"offset":1}
     * item : [{"id":"2752","name":"板式床","price":1000,"image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"},"price_basic":{"id":2752,"min":1000,"max":1000},"price_discount":{"id":2752,"min":900,"max":900}}]
     */

    private DataBean data;
    /**
     * data : {"pagination":{"total":"1","count":1,"limit":100,"offset":1},"item":[{"id":"2752","name":"板式床","price":1000,"image":{"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"},"price_basic":{"id":2752,"min":1000,"max":1000},"price_discount":{"id":2752,"min":900,"max":900}}]}
     * cmd : favorite_item
     * status : 0
     */

    private String cmd;
    private int status;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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
         * total : 1
         * count : 1
         * limit : 100
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * id : 2752
         * name : 板式床
         * price : 1000
         * image : {"small":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg","url":"http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg"}
         * price_basic : {"id":2752,"min":1000,"max":1000}
         * price_discount : {"id":2752,"min":900,"max":900}
         */

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
            private String id;
            private String name;
            private int price;
            /**
             * small : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_2.jpg
             * thumb : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378_1.jpg
             * url : http://115.159.102.231/gjj/backend/web/images/678/11e/06e/dc8/cf0a56d7a5e313ef7378.jpg
             */

            private ImageBean image;
            /**
             * id : 2752
             * min : 1000
             * max : 1000
             */

            private PriceBasicBean price_basic;
            /**
             * id : 2752
             * min : 900
             * max : 900
             */

            private PriceDiscountBean price_discount;

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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
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

            public static class PriceBasicBean {
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
            }

            public static class PriceDiscountBean {
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
            }
        }
    }
}
