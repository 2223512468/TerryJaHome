package com.jajahome.model;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/11.
 */
public class SearchBuildListModel {


    /**
     * data : {"buildings":[{"id":"10","city":{"id":"330100","name":"杭州市"},"name":"吴山人家"},{"id":"14","city":{"id":"330100","name":"杭州市"},"name":"运河人家"}]}
     * cmd : search_building
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
        private List<BuildingsBean> buildings;

        public List<BuildingsBean> getBuildings() {
            return buildings;
        }

        public void setBuildings(List<BuildingsBean> buildings) {
            this.buildings = buildings;
        }

        public static class BuildingsBean {
            /**
             * id : 10
             * city : {"id":"330100","name":"杭州市"}
             * name : 吴山人家
             */

            private String id;
            private CityBean city;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class CityBean {
                /**
                 * id : 330100
                 * name : 杭州市
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
