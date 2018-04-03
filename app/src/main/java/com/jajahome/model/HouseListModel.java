package com.jajahome.model;

import java.util.List;

/**
 *  户型数据model
 */
public class HouseListModel {

    /**
     * house : [{"id":"5","title":"A标准户型 2室2厅2卫  约96平米 ","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg"}}]
     * pagination : {"total":"1","count":1,"limit":12,"offset":1}
     */

    private DataBean data;
    /**
     * data : {"house":[{"id":"5","title":"A标准户型 2室2厅2卫  约96平米 ","image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg"}}],"pagination":{"total":"1","count":1,"limit":12,"offset":1}}
     * cmd : house_list
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
         * total : 1
         * count : 1
         * limit : 12
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * id : 5
         * title : A标准户型 2室2厅2卫  约96平米
         * image : {"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg"}
         */

        private List<HouseBean> house;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<HouseBean> getHouse() {
            return house;
        }

        public void setHouse(List<HouseBean> house) {
            this.house = house;
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

        public static class HouseBean {
            private String id;
            private String title;
            /**
             * small : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg
             * thumb : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg
             * url : http://115.159.102.231/gjj/backend/web/images/newtaozhuang/caizhi/959/d43/caa/bcd/3a2/a95/41736_414.jpg
             */

            private ImageBean image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
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
