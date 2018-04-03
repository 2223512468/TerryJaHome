package com.jajahome.model;

/**
 * Created by Administrator on 2017/6/9.
 */
public class TimeModel {

    /**
     * data : {"start_time":"2017-02-13","end_time":"2017-06-30","notice_time":"2017-05-31"}
     * cmd : get_designer_time
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
         * start_time : 2017-02-13
         * end_time : 2017-06-30
         * notice_time : 2017-05-31
         */

        private String start_time;
        private String end_time;
        private String notice_time;

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getNotice_time() {
            return notice_time;
        }

        public void setNotice_time(String notice_time) {
            this.notice_time = notice_time;
        }
    }
}
