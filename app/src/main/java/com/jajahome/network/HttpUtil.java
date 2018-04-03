package com.jajahome.network;

import android.content.Context;

import com.google.gson.Gson;
import com.jajahome.model.BaseModel;
import com.jajahome.model.LoginModle;
import com.jajahome.util.LoginUtil;
import com.jajahome.util.StringUtil;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by lhz on 2016/7/18.
 */
public class HttpUtil {
    public static RequestBody getRequestBody(String cmd) {
        Req req = new Req();
        req.setCmd(cmd);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        return requestBody;
    }

    /**
     * 获取单品 套装详情用
     *
     * @param cmd
     * @param id
     * @return
     */
    public static RequestBody getRequestBodyDetail(String cmd, String id) {
        DetailReq req = new DetailReq();
        req.setCmd(cmd);
        DetailReq.ContentBean bean = new DetailReq.ContentBean();
        bean.setId(id);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        return requestBody;
    }

    public static RequestBody getLocation(String cmd, String user_location) {
        CityReq req = new CityReq();
        req.setCmd(cmd);
        CityReq.ContentBean bean = new CityReq.ContentBean();
        bean.setUser_location(user_location);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        return requestBody;
    }

    /*VR*/
    public static RequestBody getVR(String cmd, int tzid) {
        VReq req = new VReq();
        req.setCmd(cmd);
        VReq.ContentBean bean = new VReq.ContentBean();
        bean.setTzid(tzid);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        return requestBody;

    }

    /**
     * 运单号查询
     */

    public static RequestBody getYunOrderId(String cmd, String order_id) {
        YunOrderId req = new YunOrderId();
        req.setCmd(cmd);
        YunOrderId.ContentBean bean = new YunOrderId.ContentBean();
        bean.setOrder_id(order_id);
        req.setContent(bean);
        Gson gson = new Gson();
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), gson.toJson(req));
        return requestBody;

    }


    /**
     * 获取保存的session 信息
     *
     * @param context
     * @return
     */
    public static RequestBody getSession(Context context) {
        LoginModle loginModle = LoginUtil.getInfo(context);
        if (loginModle == null) {
            return null;
        }
        RequestBody requestBody = null;
        Gson gson = new Gson();
        if (loginModle != null) {
            requestBody = RequestBody.create(MediaType.parse("application/session"), gson.toJson(loginModle.getData().getSession()));
        }
        return requestBody;
    }

    /**
     * 解析data 中数据
     *
     * @param model
     * @param classOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(BaseModel model, Class<T> classOfT) {
        if (model != null) {
            String data = model.getData();
            if (!StringUtil.isEmpty(data)) {
                Gson gson = new Gson();
                return gson.fromJson(data, classOfT);
            }
        }
        return null;
    }
}
