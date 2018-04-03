package com.jajahome.model;

import java.util.List;

/**
 * Created by ${Terry} on 2017/10/18.
 */
public class SortCityModel {

    /**
     * data : {"sort_citys":[{"firstChar":"A","citys":[{"id":"340800","name":"安庆市"}]},{"firstChar":"B","citys":[{"id":"110000","name":"北京市"},{"id":"341600","name":"亳州市"},{"id":"340300","name":"蚌埠市"},{"id":"130600","name":"保定市"}]},{"firstChar":"C","citys":[{"id":"341700","name":"池州市"},{"id":"140400","name":"长治市"},{"id":"130900","name":"沧州市"},{"id":"130800","name":"承德市"}]},{"firstChar":"D","citys":[{"id":"110101","name":"东城区"},{"id":"140200","name":"大同市"}]},{"firstChar":"F","citys":[{"id":"341200","name":"阜阳市"}]},{"firstChar":"H","citys":[{"id":"330500","name":"湖州市"},{"id":"341000","name":"黄山市"},{"id":"330100","name":"杭州市"},{"id":"340100","name":"合肥市"},{"id":"340400","name":"淮南市"},{"id":"340600","name":"淮北市"},{"id":"130400","name":"邯郸市"},{"id":"131100","name":"衡水市"}]},{"firstChar":"J","citys":[{"id":"140500","name":"晋城市"},{"id":"140700","name":"晋中市"},{"id":"330700","name":"金华市"},{"id":"330400","name":"嘉兴市"}]},{"firstChar":"L","citys":[{"id":"341500","name":"六安市"},{"id":"331100","name":"丽水市"},{"id":"131000","name":"廊坊市"}]},{"firstChar":"M","citys":[{"id":"340500","name":"马鞍山市"}]},{"firstChar":"N","citys":[{"id":"330200","name":"宁波市"}]},{"firstChar":"Q","citys":[{"id":"130300","name":"秦皇岛市"},{"id":"330800","name":"衢州市"}]},{"firstChar":"S","citys":[{"id":"341300","name":"宿州市"},{"id":"321300","name":"宿迁市"},{"id":"330600","name":"绍兴市"},{"id":"130100","name":"石家庄市"},{"id":"140600","name":"朔州市"}]},{"firstChar":"T","citys":[{"id":"321200","name":"泰州市"},{"id":"331000","name":"台州市"},{"id":"120000","name":"天津市"},{"id":"130200","name":"唐山市"},{"id":"340700","name":"铜陵市"},{"id":"140100","name":"太原市"},{"id":"341100","name":"滁州市"}]},{"firstChar":"W","citys":[{"id":"330300","name":"温州市"},{"id":"340200","name":"芜湖市"}]},{"firstChar":"X","citys":[{"id":"341800","name":"宣城市"},{"id":"110102","name":"西城区"},{"id":"130500","name":"邢台市"}]},{"firstChar":"Y","citys":[{"id":"140300","name":"阳泉市"},{"id":"140800","name":"运城市"},{"id":"320900","name":"盐城市"},{"id":"321000","name":"扬州市"}]},{"firstChar":"Z","citys":[{"id":"130700","name":"张家口市"},{"id":"321100","name":"镇江市"},{"id":"330900","name":"舟山市"}]}]}
     * cmd : sort_city_list
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
        private List<SortCitysBean> sort_citys;

        public List<SortCitysBean> getSort_citys() {
            return sort_citys;
        }

        public void setSort_citys(List<SortCitysBean> sort_citys) {
            this.sort_citys = sort_citys;
        }

        public static class SortCitysBean {
            /**
             * firstChar : A
             * citys : [{"id":"340800","name":"安庆市"}]
             */

            private String firstChar;
            private List<CitysBean> citys;

            public String getFirstChar() {
                return firstChar;
            }

            public void setFirstChar(String firstChar) {
                this.firstChar = firstChar;
            }

            public List<CitysBean> getCitys() {
                return citys;
            }

            public void setCitys(List<CitysBean> citys) {
                this.citys = citys;
            }

            public static class CitysBean {
                /**
                 * id : 340800
                 * name : 安庆市
                 */

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
}
