package com.jajahome.model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */
public class SignModel {


    /**
     * data : {"calendar":{"calendar_info":[{"sign":"0","day":1},{"sign":"0","day":2},{"sign":"0","day":3},{"sign":"0","day":4},{"sign":"0","day":5},{"sign":"0","day":6},{"sign":"0","day":7},{"sign":"0","day":8},{"sign":"0","day":9},{"sign":"0","day":10},{"sign":"0","day":11},{"sign":"0","day":12},{"sign":"0","day":13},{"sign":"0","day":14},{"sign":"0","day":15},{"sign":"0","day":16},{"sign":"0","day":17},{"sign":"0","day":18},{"sign":"0","day":19},{"sign":"0","day":20},{"sign":"0","day":21},{"sign":"0","day":22},{"sign":"0","day":23},{"sign":"0","day":24},{"sign":"0","day":25},{"sign":"0","day":26},{"sign":"0","day":27},{"sign":"0","day":28},{"sign":"0","day":29},{"sign":"0","day":30}],"signDays":0,"integral":0,"week_day":"4","totalDays":"30","dayString":"2017-06-17"}}
     * cmd : get_calendar_time
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
         * calendar : {"calendar_info":[{"sign":"0","day":1},{"sign":"0","day":2},{"sign":"0","day":3},{"sign":"0","day":4},{"sign":"0","day":5},{"sign":"0","day":6},{"sign":"0","day":7},{"sign":"0","day":8},{"sign":"0","day":9},{"sign":"0","day":10},{"sign":"0","day":11},{"sign":"0","day":12},{"sign":"0","day":13},{"sign":"0","day":14},{"sign":"0","day":15},{"sign":"0","day":16},{"sign":"0","day":17},{"sign":"0","day":18},{"sign":"0","day":19},{"sign":"0","day":20},{"sign":"0","day":21},{"sign":"0","day":22},{"sign":"0","day":23},{"sign":"0","day":24},{"sign":"0","day":25},{"sign":"0","day":26},{"sign":"0","day":27},{"sign":"0","day":28},{"sign":"0","day":29},{"sign":"0","day":30}],"signDays":0,"integral":0,"week_day":"4","totalDays":"30","dayString":"2017-06-17"}
         */

        private CalendarBean calendar;

        public CalendarBean getCalendar() {
            return calendar;
        }

        public void setCalendar(CalendarBean calendar) {
            this.calendar = calendar;
        }

        public static class CalendarBean {
            /**
             * calendar_info : [{"sign":"0","day":1},{"sign":"0","day":2},{"sign":"0","day":3},{"sign":"0","day":4},{"sign":"0","day":5},{"sign":"0","day":6},{"sign":"0","day":7},{"sign":"0","day":8},{"sign":"0","day":9},{"sign":"0","day":10},{"sign":"0","day":11},{"sign":"0","day":12},{"sign":"0","day":13},{"sign":"0","day":14},{"sign":"0","day":15},{"sign":"0","day":16},{"sign":"0","day":17},{"sign":"0","day":18},{"sign":"0","day":19},{"sign":"0","day":20},{"sign":"0","day":21},{"sign":"0","day":22},{"sign":"0","day":23},{"sign":"0","day":24},{"sign":"0","day":25},{"sign":"0","day":26},{"sign":"0","day":27},{"sign":"0","day":28},{"sign":"0","day":29},{"sign":"0","day":30}]
             * signDays : 0
             * integral : 0
             * week_day : 4
             * totalDays : 30
             * dayString : 2017-06-17
             */

            private int signDays;
            private int integral;
            private String week_day;
            private String totalDays;
            private String dayString;
            private int totalIntegral;

            public int getTotalIntegral() {
                return totalIntegral;
            }

            public void setTotalIntegral(int totalIntegral) {
                this.totalIntegral = totalIntegral;
            }

            private List<CalendarInfoBean> calendar_info;

            public int getSignDays() {
                return signDays;
            }

            public void setSignDays(int signDays) {
                this.signDays = signDays;
            }

            public int getIntegral() {
                return integral;
            }

            public void setIntegral(int integral) {
                this.integral = integral;
            }

            public String getWeek_day() {
                return week_day;
            }

            public void setWeek_day(String week_day) {
                this.week_day = week_day;
            }

            public String getTotalDays() {
                return totalDays;
            }

            public void setTotalDays(String totalDays) {
                this.totalDays = totalDays;
            }

            public String getDayString() {
                return dayString;
            }

            public void setDayString(String dayString) {
                this.dayString = dayString;
            }

            public List<CalendarInfoBean> getCalendar_info() {
                return calendar_info;
            }

            public void setCalendar_info(List<CalendarInfoBean> calendar_info) {
                this.calendar_info = calendar_info;
            }

            public static class CalendarInfoBean {
                /**
                 * sign : 0
                 * day : 1
                 */

                private String sign;
                private int day;

                public String getSign() {
                    return sign;
                }

                public void setSign(String sign) {
                    this.sign = sign;
                }

                public int getDay() {
                    return day;
                }

                public void setDay(int day) {
                    this.day = day;
                }
            }
        }
    }
}
