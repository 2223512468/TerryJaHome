package com.jajahome.model;

import java.util.List;

/**
 * 城市选择器 数据model
 */
public class AreaModel {

    /**
     * cmd : arealist
     * data : {"area":[{"city":["石家庄市","唐山市","秦皇岛市","邯郸市","邢台市","保定市","张家口市","承德市","沧州市","廊坊市","衡水市"],"id":"130000","province":"河北省"},{"city":["太原市","大同市","阳泉市","长治市","晋城市","朔州市","晋中市","运城市","忻州市","临汾市","吕梁市"],"id":"140000","province":"山西省"},{"city":["呼和浩特市","包头市","乌海市","赤峰市","通辽市","鄂尔多斯市","呼伦贝尔市","巴彦淖尔市","乌兰察布市","兴安盟","锡林郭勒盟","阿拉善盟"],"id":"150000","province":"内蒙古自治区"}]}
     * status : 0
     */

    private String cmd;
    private DataBean data;
    private int status;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * city : ["石家庄市","唐山市","秦皇岛市","邯郸市","邢台市","保定市","张家口市","承德市","沧州市","廊坊市","衡水市"]
         * id : 130000
         * province : 河北省
         */

        private List<AreaBean> area;

        public List<AreaBean> getArea() {
            return area;
        }

        public void setArea(List<AreaBean> area) {
            this.area = area;
        }

        public static class AreaBean {
            private String id;
            private String province;
            private List<CityBean> city;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public List<CityBean> getCity() {
                return city;
            }

            public void setCity(List<CityBean> city) {
                this.city = city;
            }


            public static class CityBean {

                private String id;
                private String city;
                private List<CountyBean> county;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public List<CountyBean> getCounty() {
                    return county;
                }

                public void setCounty(List<CountyBean> county) {
                    this.county = county;
                }


                public static class CountyBean {

                    private int id;
                    private String name;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
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
}
