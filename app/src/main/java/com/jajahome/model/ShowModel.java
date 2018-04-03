package com.jajahome.model;

import java.util.List;

/**
 * show家详情model
 */
public class ShowModel {

    /**
     * pagination : {"total":"2","count":1,"limit":100,"offset":1}
     * show : [{"id":"37","name":"线.律","image":{"small":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_2.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_1.jpg","url":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c.jpg"},"type":"3"},{"id":"34","name":"一点茶事儿","image":{"small":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345_2.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345_1.jpg","url":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345.jpg"},"type":"3"}]
     */

    private DataBean data;
    /**
     * data : {"pagination":{"total":"2","count":1,"limit":100,"offset":1},"show":[{"id":"37","name":"线.律","image":{"small":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_2.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_1.jpg","url":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c.jpg"},"type":"3"},{"id":"34","name":"一点茶事儿","image":{"small":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345_2.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345_1.jpg","url":"http://115.159.75.31/gjj/backend/web/images/0de/ca0/411/a5d/10385c40af3650da4345.jpg"},"type":"3"}]}
     * cmd : favorite_show
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
         * total : 2
         * count : 1
         * limit : 100
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * id : 37
         * name : 线.律
         * image : {"small":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_2.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_1.jpg","url":"http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c.jpg"}
         * type : 3
         */

        private List<ShowBean> show;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<ShowBean> getShow() {
            return show;
        }

        public void setShow(List<ShowBean> show) {
            this.show = show;
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

        public static class ShowBean {
            private String id;
            private String name;
            /**
             * small : http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_2.jpg
             * thumb : http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c_1.jpg
             * url : http://115.159.75.31/gjj/backend/web/images/339/cc4/de5/628/dcbe7a6dfc5854e44b7c.jpg
             */

            private ImageBean image;
            private String type;

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

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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
