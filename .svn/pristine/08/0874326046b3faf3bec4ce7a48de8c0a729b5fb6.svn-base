package com.jajahome.model;

import java.io.Serializable;
import java.util.List;

/**
 * 秀家详情model
 */
public class ShowDetailModel implements Serializable {

    /**
     * show : {"id":"3","name":"美式客厅真皮沙发","preview":{"small":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","url":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg"},"width":"1152","height":"768","type":0,"list":[{"id":"4","pos":{"x":575,"y":409},"url":"","action":"item","action_id":"1523"}],"content":""}
     */

    private DataBean data;
    /**
     * data : {"show":{"id":"3","name":"美式客厅真皮沙发","preview":{"small":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","url":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg"},"width":"1152","height":"768","type":0,"list":[{"id":"4","pos":{"x":575,"y":409},"url":"","action":"item","action_id":"1523"}],"content":""}}
     * cmd : show
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

    public static class DataBean implements Serializable{
        /**
         * id : 3
         * name : 美式客厅真皮沙发
         * preview : {"small":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","thumb":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg","url":"http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg"}
         * width : 1152
         * height : 768
         * type : 0
         * list : [{"id":"4","pos":{"x":575,"y":409},"url":"","action":"item","action_id":"1523"}]
         * content :
         */

        private ShowBean show;

        public ShowBean getShow() {
            return show;
        }

        public void setShow(ShowBean show) {
            this.show = show;
        }

        public static class ShowBean implements Serializable{
            private String id;
            private String name;
            private int favorite;

            public int getFavorite() {
                return favorite;
            }

            public void setFavorite(int favorite) {
                this.favorite = favorite;
            }

            /**
             * small : http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg
             * thumb : http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg
             * url : http://115.159.75.31/gjj/backend/web/images/xiujia/keting/1dd/4db/d39/f12/5df/854/33806_486.jpg
             */

            private PreviewBean preview;
            private String width;
            private String height;
            private int type;
            private String content;
            /**
             * id : 4
             * pos : {"x":575,"y":409}
             * url :
             * action : item
             * action_id : 1523
             */

            private List<ListBean> list;

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

            public PreviewBean getPreview() {
                return preview;
            }

            public void setPreview(PreviewBean preview) {
                this.preview = preview;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public List<ListBean> getList() {
                return list;
            }

            public void setList(List<ListBean> list) {
                this.list = list;
            }

            public static class PreviewBean implements Serializable {
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

            public static class ListBean implements Serializable {
                private String id;
                /**
                 * x : 575
                 * y : 409
                 */

                private PosBean pos;
                private String url;
                private String action;
                private String action_id;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public PosBean getPos() {
                    return pos;
                }

                public void setPos(PosBean pos) {
                    this.pos = pos;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
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

                public static class PosBean implements Serializable {
                    private int x;
                    private int y;

                    public int getX() {
                        return x;
                    }

                    public void setX(int x) {
                        this.x = x;
                    }

                    public int getY() {
                        return y;
                    }

                    public void setY(int y) {
                        this.y = y;
                    }
                }
            }
        }
    }
}
