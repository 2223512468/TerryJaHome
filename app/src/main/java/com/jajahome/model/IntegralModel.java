package com.jajahome.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/9.
 */
public class IntegralModel {


    /**
     * data : {"pagination":{"total":"2","count":1,"limit":12,"offset":1},"integralInfo":{"available":"199","integral_list":[{"variable_amount":"299","type":"1","time":"1494295101"},{"variable_amount":"-100","type":"100","time":"1494295101"}]}}
     * cmd : integral_details
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
         * pagination : {"total":"2","count":1,"limit":12,"offset":1}
         * integralInfo : {"available":"199","integral_list":[{"variable_amount":"299","type":"1","time":"1494295101"},{"variable_amount":"-100","type":"100","time":"1494295101"}]}
         */

        private PaginationBean pagination;
        private IntegralInfoBean integralInfo;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public IntegralInfoBean getIntegralInfo() {
            return integralInfo;
        }

        public void setIntegralInfo(IntegralInfoBean integralInfo) {
            this.integralInfo = integralInfo;
        }

        public static class PaginationBean {
            /**
             * total : 2
             * count : 1
             * limit : 12
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

        public static class IntegralInfoBean {
            /**
             * available : 199
             * integral_list : [{"variable_amount":"299","type":"1","time":"1494295101"},{"variable_amount":"-100","type":"100","time":"1494295101"}]
             */

            private String available;
            private List<IntegralListBean> integral_list;

            public String getAvailable() {
                return available;
            }

            public void setAvailable(String available) {
                this.available = available;
            }

            public List<IntegralListBean> getIntegral_list() {
                return integral_list;
            }

            public void setIntegral_list(List<IntegralListBean> integral_list) {
                this.integral_list = integral_list;
            }

            public static class IntegralListBean {
                /**
                 * variable_amount : 299
                 * type : 1
                 * time : 1494295101
                 */

                private String variable_amount;
                private String type;
                private String time;

                public String getVariable_amount() {
                    return variable_amount;
                }

                public void setVariable_amount(String variable_amount) {
                    this.variable_amount = variable_amount;
                }

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }
}
