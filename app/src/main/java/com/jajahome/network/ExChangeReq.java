package com.jajahome.network;

/**
 * Created by Administrator on 2017/8/16.
 */
public class ExChangeReq {


    private String cmd;
    private ContentBean content;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ContentBean getContentBean() {
        return content;
    }

    public void setContentBean(ContentBean contentBean) {
        this.content = contentBean;
    }

    public static class ContentBean {

        private Pagination pagination;

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }

        public static class Pagination {
            private int limit;
            private int offset;

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

    }

}
