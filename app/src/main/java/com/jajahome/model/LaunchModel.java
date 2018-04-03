package com.jajahome.model;

/**
 * Created by Administrator on 2017/4/13.
 */
public class LaunchModel {


    /**
     * data : {"launchImage":"http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/xitongyong/b3f/a64/98c/4c3/4b5/725/43251_491.jpg"}
     * cmd : launch_Image
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
         * launchImage : http://www.jajahome.com/gjj/backend/web/images/newtaozhuang/xitongyong/b3f/a64/98c/4c3/4b5/725/43251_491.jpg
         */

        private String launchImage;

        public String getLaunchImage() {
            return launchImage;
        }

        public void setLaunchImage(String launchImage) {
            this.launchImage = launchImage;
        }
    }
}
