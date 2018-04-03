package com.jajahome.network;

import com.jajahome.constant.Constant;

/**
 * Created by Administrator on 2017/5/8.
 */
public class RebateReq {

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

        private PaginationBean pagination;
        private int type;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

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

    }
}
