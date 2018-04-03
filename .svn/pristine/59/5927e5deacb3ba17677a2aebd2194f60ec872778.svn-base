package com.jajahome.model;

import java.util.List;

/**
 * Created by ${Terry} on 2017/11/11.
 */
public class BuildListModel {


    /**
     * data : {"sort_buildings":[{"firstChar":"Q","buildings":[{"id":"7","name":"钱塘明月"}]},{"firstChar":"R","buildings":[{"id":"8","name":"融信杭州公馆"}]},{"firstChar":"W","buildings":[{"id":"1","name":"武林一号"},{"id":"9","name":"万泰城章"},{"id":"10","name":"吴山人家"}]},{"firstChar":"X","buildings":[{"id":"12","name":"溪上玫瑰园"}]},{"firstChar":"Y","buildings":[{"id":"2","name":"杨柳郡"},{"id":"11","name":"阳光郡"}]}]}
     * cmd : sort_building_list
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
        private List<SortBuildingsBean> sort_buildings;

        public List<SortBuildingsBean> getSort_buildings() {
            return sort_buildings;
        }

        public void setSort_buildings(List<SortBuildingsBean> sort_buildings) {
            this.sort_buildings = sort_buildings;
        }

        public static class SortBuildingsBean {
            /**
             * firstChar : Q
             * buildings : [{"id":"7","name":"钱塘明月"}]
             */

            private String firstChar;
            private List<BuildingsBean> buildings;

            public String getFirstChar() {
                return firstChar;
            }

            public void setFirstChar(String firstChar) {
                this.firstChar = firstChar;
            }

            public List<BuildingsBean> getBuildings() {
                return buildings;
            }

            public void setBuildings(List<BuildingsBean> buildings) {
                this.buildings = buildings;
            }

            public static class BuildingsBean {
                /**
                 * id : 7
                 * name : 钱塘明月
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
