package com.jajahome.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */
public class RebateInfoModel {


    /**
     * data : {"pagination":{"total":"6","count":1,"limit":12,"offset":1},"rebateInfo":{"available":1943.49,"cashback":"57.00","rebate_list":[{"variable_amount":2000.49,"type":0,"time":"1496896888","status":3},{"variable_amount":-10,"type":1,"time":"0","status":3},{"variable_amount":-2,"type":1,"time":"0","status":3},{"variable_amount":"-0","type":1,"time":"0","status":3},{"variable_amount":-1,"type":1,"time":"0","status":3},{"variable_amount":-33,"type":1,"time":"0","status":3}]}}
     * cmd : rebate_details
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
         * pagination : {"total":"6","count":1,"limit":12,"offset":1}
         * rebateInfo : {"available":1943.49,"cashback":"57.00","rebate_list":[{"variable_amount":2000.49,"type":0,"time":"1496896888","status":3},{"variable_amount":-10,"type":1,"time":"0","status":3},{"variable_amount":-2,"type":1,"time":"0","status":3},{"variable_amount":"-0","type":1,"time":"0","status":3},{"variable_amount":-1,"type":1,"time":"0","status":3},{"variable_amount":-33,"type":1,"time":"0","status":3}]}
         */

        private PaginationBean pagination;
        private RebateInfoBean rebateInfo;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public RebateInfoBean getRebateInfo() {
            return rebateInfo;
        }

        public void setRebateInfo(RebateInfoBean rebateInfo) {
            this.rebateInfo = rebateInfo;
        }

        public static class PaginationBean {
            /**
             * total : 6
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

        public static class RebateInfoBean {
            /**
             * available : 1943.49
             * cashback : 57.00
             * rebate_list : [{"variable_amount":2000.49,"type":0,"time":"1496896888","status":3},{"variable_amount":-10,"type":1,"time":"0","status":3},{"variable_amount":-2,"type":1,"time":"0","status":3},{"variable_amount":"-0","type":1,"time":"0","status":3},{"variable_amount":-1,"type":1,"time":"0","status":3},{"variable_amount":-33,"type":1,"time":"0","status":3}]
             */

            private double available;
            private String cashback;
            private List<RebateListBean> rebate_list;

            public double getAvailable() {
                return available;
            }

            public void setAvailable(double available) {
                this.available = available;
            }

            public String getCashback() {
                return cashback;
            }

            public void setCashback(String cashback) {
                this.cashback = cashback;
            }

            public List<RebateListBean> getRebate_list() {
                return rebate_list;
            }

            public void setRebate_list(List<RebateListBean> rebate_list) {
                this.rebate_list = rebate_list;
            }

            public static class RebateListBean {
                /**
                 * variable_amount : 2000.49
                 * type : 0
                 * time : 1496896888
                 * status : 3
                 */

                private double variable_amount;
                private int type;
                private String time;
                private int status;

                public double getVariable_amount() {
                    return variable_amount;
                }

                public void setVariable_amount(double variable_amount) {
                    this.variable_amount = variable_amount;
                }

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }
            }
        }
    }
}
