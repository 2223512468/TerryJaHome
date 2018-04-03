package com.jajahome.model;

import java.util.List;

/**
 * Created by ${Terry} on 2017/10/18.
 */
public class HouseModel {


    /**
     * data : {"pagination":{"total":"8","count":1,"limit":100,"offset":1},"house":[{"buildings":{"id":"1","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg"},"title":"三室两厅二卫 90平米","favourite":1}},{"buildings":{"id":"15","city":{"id":"330100","name":"杭州市"},"name":"钱塘明月"},"house":{"id":"7","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3.png"},"title":"钱塘明月三室二厅","favourite":1}},{"buildings":{"id":"19","city":{"id":"330100","name":"杭州市"},"name":"云峰家园"},"house":{"id":"13","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554.jpg"},"title":"三室二厅一厨二卫","favourite":1}},{"buildings":{"id":"2","city":{"id":"330100","name":"杭州市"},"name":"杨柳郡"},"house":{"id":"2","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg"},"title":"四室两厅三卫 140平米","favourite":1}},{"buildings":{"id":"3","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg"},"title":"G户型 四室两厅五卫 334平米","favourite":1}},{"buildings":{"id":"4","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg"},"title":"C户型 六房两厅六卫 640平米","favourite":1}},{"buildings":{"id":"18","city":{"id":"330100","name":"杭州市"},"name":"溪上玫瑰园"},"house":{"id":"12","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b.png"},"title":"溪上三室二厅一厨一卫","favourite":1}},{"buildings":{"id":"10","city":{"id":"330200","name":"宁波市"},"name":"宁波保亿风景御园"},"house":{"id":"5","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484.jpg"},"title":"B户型  三室二厅二卫  123平米","favourite":1}}]}
     * cmd : get_favourt_building
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
         * pagination : {"total":"8","count":1,"limit":100,"offset":1}
         * house : [{"buildings":{"id":"1","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg"},"title":"三室两厅二卫 90平米","favourite":1}},{"buildings":{"id":"15","city":{"id":"330100","name":"杭州市"},"name":"钱塘明月"},"house":{"id":"7","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/142/969/22d/0c8/cf08f14a8c58d06992c3.png"},"title":"钱塘明月三室二厅","favourite":1}},{"buildings":{"id":"19","city":{"id":"330100","name":"杭州市"},"name":"云峰家园"},"house":{"id":"13","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/636/919/18f/ae0/a114c712cacf86ab2554.jpg"},"title":"三室二厅一厨二卫","favourite":1}},{"buildings":{"id":"2","city":{"id":"330100","name":"杭州市"},"name":"杨柳郡"},"house":{"id":"2","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/718/32a/e11/091/b98/9cd/41732_414.jpg"},"title":"四室两厅三卫 140平米","favourite":1}},{"buildings":{"id":"3","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/692/6db/f00/f9a/5cb/30c/41734_414.jpg"},"title":"G户型 四室两厅五卫 334平米","favourite":1}},{"buildings":{"id":"4","city":{"id":"330100","name":"杭州市"},"name":"武林一号"},"house":{"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/be5/bcf/ec0/a1b/000/be4/41735_414.jpg"},"title":"C户型 六房两厅六卫 640平米","favourite":1}},{"buildings":{"id":"18","city":{"id":"330100","name":"杭州市"},"name":"溪上玫瑰园"},"house":{"id":"12","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b_2.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b_1.png","url":"http://www.jajahome.com/gjj/backend/web/images/466/125/9f2/754/3eb540e1e76c56735b1b.png"},"title":"溪上三室二厅一厨一卫","favourite":1}},{"buildings":{"id":"10","city":{"id":"330200","name":"宁波市"},"name":"宁波保亿风景御园"},"house":{"id":"5","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484_2.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484_1.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/f3d/33a/cec/1a6/176db25b0df0fa2aa484.jpg"},"title":"B户型  三室二厅二卫  123平米","favourite":1}}]
         */

        private PaginationBean pagination;
        private List<HouseBeanX> house;

        public PaginationBean getPagination() {
            return pagination;
        }

        public void setPagination(PaginationBean pagination) {
            this.pagination = pagination;
        }

        public List<HouseBeanX> getHouse() {
            return house;
        }

        public void setHouse(List<HouseBeanX> house) {
            this.house = house;
        }

        public static class PaginationBean {
            /**
             * total : 8
             * count : 1
             * limit : 100
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

        public static class HouseBeanX {
            /**
             * buildings : {"id":"1","city":{"id":"330100","name":"杭州市"},"name":"武林一号"}
             * house : {"id":"1","image":{"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg"},"title":"三室两厅二卫 90平米","favourite":1}
             */

            private BuildingsBean buildings;
            private HouseBean house;

            public BuildingsBean getBuildings() {
                return buildings;
            }

            public void setBuildings(BuildingsBean buildings) {
                this.buildings = buildings;
            }

            public HouseBean getHouse() {
                return house;
            }

            public void setHouse(HouseBean house) {
                this.house = house;
            }

            public static class BuildingsBean {
                /**
                 * id : 1
                 * city : {"id":"330100","name":"杭州市"}
                 * name : 武林一号
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

            public static class HouseBean {
                /**
                 * id : 1
                 * image : {"small":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","thumb":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg","url":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg"}
                 * title : 三室两厅二卫 90平米
                 * favourite : 1
                 */

                private String id;
                private ImageBean image;
                private String title;
                private int favourite;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public ImageBean getImage() {
                    return image;
                }

                public void setImage(ImageBean image) {
                    this.image = image;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public int getFavourite() {
                    return favourite;
                }

                public void setFavourite(int favourite) {
                    this.favourite = favourite;
                }

                public static class ImageBean {
                    /**
                     * small : http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg
                     * thumb : http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg
                     * url : http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/caizhi/b3b/71f/b74/3dd/30c/223/41733_414.jpg
                     */

                    private String small;
                    private String thumb;
                    private String url;

                    public String getSmall() {
                        return small;
                    }

                    public void setSmall(String small) {
                        this.small = small;
                    }

                    public String getThumb() {
                        return thumb;
                    }

                    public void setThumb(String thumb) {
                        this.thumb = thumb;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }
                }
            }
        }
    }
}
