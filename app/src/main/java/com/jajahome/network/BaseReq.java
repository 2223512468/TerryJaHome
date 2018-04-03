package com.jajahome.network;

import com.jajahome.model.LoginModle;

/**
 * 基础 网络请求体封装
 */
public class BaseReq {

    /**
     * cmd : home
     * content : {"id":1}
     */

    private String cmd;
    /**
     * id : 1
     */

    private ContentBean content;


    public LoginModle.DataBean.SessionBean getSession() {
        return session;
    }

    public void setSession(LoginModle.DataBean.SessionBean session) {
        this.session = session;
    }

    private LoginModle.DataBean.SessionBean session;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }



    public static class ContentBean {
        private int id;
        private Integer log_id;
        private String mobile;
        private String username;
        private String password;
        private String sms_token;
        private String invite_code;
        private String avatar;
        private String old_password;
        private String new_password;
        private int pagination;

        public Integer getOperation() {
            return operation;
        }

        public void setOperation(Integer operation) {
            this.operation = operation;
        }

        private Integer operation;
        private int type;
        private AddressBean address;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private String title;
        private String content;
        private String nickname;
        private String sex;
        private String birthday;

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

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getPagination() {
            return pagination;
        }

        public void setPagination(int pagination) {
            this.pagination = pagination;
        }

        public LoginModle.DataBean.SessionBean getSession() {
            return session;
        }

        public void setSession(LoginModle.DataBean.SessionBean session) {
            this.session = session;
        }

        public LoginModle.DataBean.SessionBean session;

        public String getOld_password() {
            return old_password;
        }

        public void setOld_password(String old_password) {
            this.old_password = old_password;
        }

        public String getNew_password() {
            return new_password;
        }

        public void setNew_password(String new_password) {
            this.new_password = new_password;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSms_token() {
            return sms_token;
        }

        public void setSms_token(String sms_token) {
            this.sms_token = sms_token;
        }

        public String getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(String invite_code) {
            this.invite_code = invite_code;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getLog_id() {
            return log_id;
        }

        public void setLog_id(Integer log_id) {
            this.log_id = log_id;
        }

        public static class AddressBean {
            private int type;
            private String area;
            private String detail_address;
            private String postcode;
            private String tel;
            private String name;
            private String mobile;
            private String id;

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getDetail_address() {
                return detail_address;
            }

            public void setDetail_address(String detail_address) {
                this.detail_address = detail_address;
            }

            public String getPostcode() {
                return postcode;
            }

            public void setPostcode(String postcode) {
                this.postcode = postcode;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
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
