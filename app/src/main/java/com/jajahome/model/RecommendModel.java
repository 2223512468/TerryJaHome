package com.jajahome.model;

import java.util.List;

/**
 * 评价model
 */
public class RecommendModel {

    private DataBean data;
    /**
     * data : {"recommend":[{"id":"19","name":"比赛结果公告","url":null,"designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png","thumb":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png","url":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png"},"action":"show","action_id":"11"},{"id":"11","name":"URL链接测试","url":"http://www.sohu.com","designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/ca7/361/e58/d87/ff5/fc2/39327_200.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/ca7/361/e58/d87/ff5/fc2/39327_200.jpg","url":"http://115.159.102.231/gjj/backend/web/images/newtaozhuang/ca7/361/e58/d87/ff5/fc2/39327_200.jpg"},"action":"","action_id":"0"},{"id":"5","name":"法式卧室","url":"http://www.sina.cn","designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7611_112.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7611_112.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7611_112.jpg"},"action":"set","action_id":"1"},{"id":"8","name":"现代书房","url":"www.baidu.com","designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7607_112.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7607_112.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7607_112.jpg"},"action":"set","action_id":"4"},{"id":"7","name":"现代餐厅","url":"http://www.cctv.com","designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7610_112.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7610_112.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7610_112.jpg"},"action":"set","action_id":"3"},{"id":"6","name":"现代卧室","url":"http://google.cn","designer":{"invitecode":"","invitecode1":""},"image":{"small":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7616_112.jpg","thumb":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7616_112.jpg","url":"http://115.159.102.231/gjj/backend/web/images/home_page/recommend/big/7616_112.jpg"},"action":"set","action_id":"2"}]}
     * cmd : recommend
     * status : 0
     */

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
         * id : 19
         * name : 比赛结果公告
         * url : null
         * designer : {"invitecode":"","invitecode1":""}
         * image : {"small":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png","thumb":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png","url":"http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png"}
         * action : show
         * action_id : 11
         */

        private List<RecommendBean> recommend;

        public List<RecommendBean> getRecommend() {
            return recommend;
        }

        public void setRecommend(List<RecommendBean> recommend) {
            this.recommend = recommend;
        }

        public static class RecommendBean {
            private String id;
            private String name;
            private String url;
            /**
             * invitecode :
             * invitecode1 :
             */

            private DesignerBean designer;
            /**
             * small : http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png
             * thumb : http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png
             * url : http://115.159.102.231/gjj/backend/web/images/xiujia/gonggao/584/c77/377/bff/761/941/39385_494.png
             */

            private ImageBean image;
            private String action;
            private String action_id;
            private int action_show;

            public int getAction_show() {
                return action_show;
            }

            public void setAction_show(int action_show) {
                this.action_show = action_show;
            }

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public DesignerBean getDesigner() {
                return designer;
            }

            public void setDesigner(DesignerBean designer) {
                this.designer = designer;
            }

            public ImageBean getImage() {
                return image;
            }

            public void setImage(ImageBean image) {
                this.image = image;
            }

            public String getAction() {
                return action;
            }

            public void setAction(String action) {
                this.action = action;
            }

            public String getAction_id() {
                return action_id;
            }

            public void setAction_id(String action_id) {
                this.action_id = action_id;
            }

            public static class DesignerBean {
                private String invitecode;
                private String invitecode1;

                public String getInvitecode() {
                    return invitecode;
                }

                public void setInvitecode(String invitecode) {
                    this.invitecode = invitecode;
                }

                public String getInvitecode1() {
                    return invitecode1;
                }

                public void setInvitecode1(String invitecode1) {
                    this.invitecode1 = invitecode1;
                }
            }

            public static class ImageBean {
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
