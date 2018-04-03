package com.jajahome.model;

/**
 * Created by Administrator on 2017/3/29.
 */
public class WeiXinLoginModel {


    /**
     * data : {"session":{"sid":"862527","token":"c078d0a290938b3c8f2a8a399509aaon1nrvuI"},"user":{"id":"2091","avatar":{"small":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","url":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2465_184.png"},"username":"on1nrvuIlMsTUCX_vbolROyqokUs","nickname":"Terry","sex":"男","type":"1","birthday":"","mobile":"on1nrvuIlMsTUCX_vb","rank":0,"invitecode":"3hztrf","invitecode1":""}}
     * cmd : user_signin_weixin
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
         * session : {"sid":"862527","token":"c078d0a290938b3c8f2a8a399509aaon1nrvuI"}
         * user : {"id":"2091","avatar":{"small":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","url":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2465_184.png"},"username":"on1nrvuIlMsTUCX_vbolROyqokUs","nickname":"Terry","sex":"男","type":"1","birthday":"","mobile":"on1nrvuIlMsTUCX_vb","rank":0,"invitecode":"3hztrf","invitecode1":""}
         */

        private SessionBean session;
        private UserBean user;

        public SessionBean getSession() {
            return session;
        }

        public void setSession(SessionBean session) {
            this.session = session;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class SessionBean {
            /**
             * sid : 862527
             * token : c078d0a290938b3c8f2a8a399509aaon1nrvuI
             */

            private String sid;
            private String token;

            public String getSid() {
                return sid;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public String getToken() {
                return token;
            }

            public void setToken(String token) {
                this.token = token;
            }
        }

        public static class UserBean {
            /**
             * id : 2091
             * avatar : {"small":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","thumb":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png","url":"http://www.jajahome.com/gjj/backend/web/images/designer/default/2465_184.png"}
             * username : on1nrvuIlMsTUCX_vbolROyqokUs
             * nickname : Terry
             * sex : 男
             * type : 1
             * birthday :
             * mobile : on1nrvuIlMsTUCX_vb
             * rank : 0
             * invitecode : 3hztrf
             * invitecode1 :
             */

            private String id;
            private AvatarBean avatar;
            private String username;
            private String nickname;
            private String sex;
            private String type;
            private String birthday;
            private String mobile;
            private int rank;
            private String invitecode;
            private String invitecode1;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public AvatarBean getAvatar() {
                return avatar;
            }

            public void setAvatar(AvatarBean avatar) {
                this.avatar = avatar;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

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

            public static class AvatarBean {
                /**
                 * small : http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png
                 * thumb : http://www.jajahome.com/gjj/backend/web/images/designer/default/2464_184.png
                 * url : http://www.jajahome.com/gjj/backend/web/images/designer/default/2465_184.png
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
