package com.jajahome.model;

import java.util.List;

/**
 * 楼盘列表model
 */
public class BuildingModel  {

    /**
     * building : [{"id":"2","name":"杨柳郡"},{"id":"1","name":"武林一号"}]
     * pagination : {"total":"2","count":1,"limit":12,"offset":1}
     */

    private DataBean data;
    /**
     * data : {"building":[{"id":"2","name":"杨柳郡"},{"id":"1","name":"武林一号"}],"pagination":{"total":"2","count":1,"limit":12,"offset":1}}
     * cmd : buliding_list
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
         * total : 2
         * count : 1
         * limit : 12
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * id : 2
         * name : 杨柳郡
         */

        private List<BuildingBean> building;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<BuildingBean> getBuilding() {
            return building;
        }

        public void setBuilding(List<BuildingBean> building) {
            this.building = building;
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

        public static class BuildingBean {
            private String id;
            private String name;

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
        }
    }
}
