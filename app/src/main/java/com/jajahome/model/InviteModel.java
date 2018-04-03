package com.jajahome.model;

import java.util.List;

/**
 * 邀请人model
 */
public class InviteModel {
    /**
     * pagination : {"total":"6","count":1,"limit":100,"offset":1}
     * invites : [{"phone":"151xxxx2337","time":"2016-08-29 14:39:54","pay_amount":12602,"one_friends":0,"one_payAmounts":0},{"phone":"186xxxx9056","time":"2016-06-16 12:23:26","pay_amount":12600,"one_friends":1,"one_payAmounts":0},{"phone":"158xxxx1004","time":"2016-06-12 10:14:59","pay_amount":0,"one_friends":0,"one_payAmounts":0},{"phone":"182xxxx4071","time":"2015-12-02 21:01:22","pay_amount":0,"one_friends":0,"one_payAmounts":0},{"phone":"150xxxx7330","time":"2016-08-03 16:41:14","pay_amount":17400,"one_friends":3,"one_payAmounts":900},{"phone":"180xxxx7946","time":"2016-08-17 09:40:52","pay_amount":900,"one_friends":0,"one_payAmounts":0}]
     * total : {"one_friends":6,"one_payAmounts":43502,"two_friends":4,"two_payAmounts":900}
     */

    private DataBean data;
    /**
     * data : {"pagination":{"total":"6","count":1,"limit":100,"offset":1},"invites":[{"phone":"151xxxx2337","time":"2016-08-29 14:39:54","pay_amount":12602,"one_friends":0,"one_payAmounts":0},{"phone":"186xxxx9056","time":"2016-06-16 12:23:26","pay_amount":12600,"one_friends":1,"one_payAmounts":0},{"phone":"158xxxx1004","time":"2016-06-12 10:14:59","pay_amount":0,"one_friends":0,"one_payAmounts":0},{"phone":"182xxxx4071","time":"2015-12-02 21:01:22","pay_amount":0,"one_friends":0,"one_payAmounts":0},{"phone":"150xxxx7330","time":"2016-08-03 16:41:14","pay_amount":17400,"one_friends":3,"one_payAmounts":900},{"phone":"180xxxx7946","time":"2016-08-17 09:40:52","pay_amount":900,"one_friends":0,"one_payAmounts":0}],"total":{"one_friends":6,"one_payAmounts":43502,"two_friends":4,"two_payAmounts":900}}
     * cmd : invite_list
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
         * total : 6
         * count : 1
         * limit : 100
         * offset : 1
         */

        private PaginationBean pagination;
        /**
         * one_friends : 6
         * one_payAmounts : 43502
         * two_friends : 4
         * two_payAmounts : 900
         */

        private TotalBean total;
        /**
         * phone : 151xxxx2337
         * time : 2016-08-29 14:39:54
         * pay_amount : 12602
         * one_friends : 0
         * one_payAmounts : 0
         */

        private List<InvitesBean> invites;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public TotalBean getTotal() {
            return total;
        }

        public void setTotal(TotalBean total) {
            this.total = total;
        }

        public List<InvitesBean> getInvites() {
            return invites;
        }

        public void setInvites(List<InvitesBean> invites) {
            this.invites = invites;
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

        public static class TotalBean {
            private int one_friends;
            private int one_payAmounts;
            private int two_friends;
            private int two_payAmounts;
            private int my_payAmounts;

            public int getMy_payAmounts() {
                return my_payAmounts;
            }

            public void setMy_payAmounts(int my_payAmounts) {
                this.my_payAmounts = my_payAmounts;
            }

            public int getOne_friends() {
                return one_friends;
            }

            public void setOne_friends(int one_friends) {
                this.one_friends = one_friends;
            }

            public int getOne_payAmounts() {
                return one_payAmounts;
            }

            public void setOne_payAmounts(int one_payAmounts) {
                this.one_payAmounts = one_payAmounts;
            }

            public int getTwo_friends() {
                return two_friends;
            }

            public void setTwo_friends(int two_friends) {
                this.two_friends = two_friends;
            }

            public int getTwo_payAmounts() {
                return two_payAmounts;
            }

            public void setTwo_payAmounts(int two_payAmounts) {
                this.two_payAmounts = two_payAmounts;
            }
        }

        public static class InvitesBean {
            private String phone;
            private String time;
            private int pay_amount;
            private int one_friends;
            private int one_payAmounts;

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getPay_amount() {
                return pay_amount;
            }

            public void setPay_amount(int pay_amount) {
                this.pay_amount = pay_amount;
            }

            public int getOne_friends() {
                return one_friends;
            }

            public void setOne_friends(int one_friends) {
                this.one_friends = one_friends;
            }

            public int getOne_payAmounts() {
                return one_payAmounts;
            }

            public void setOne_payAmounts(int one_payAmounts) {
                this.one_payAmounts = one_payAmounts;
            }
        }
    }
}
