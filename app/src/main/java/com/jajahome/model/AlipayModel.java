package com.jajahome.model;

/**
 * 支付宝支付回调接口
 */
public class AlipayModel {

    /**
     * order_string : _input_charset="utf-8"&body="JAJAHOME付款 900 元"&notify_url="http://115.159.102.231/furniture/call_back_zfb_pay.php"&out_trade_no="733020531472202094"&partner="2088121185691103"&payment_type="1"&seller_id="2930643693@qq.com"&service="mobile.securitypay.pay"&sign="doBcns4bfcaNmAF4k0GZ67vdhyIyj3%2BcgN3WJ3z9qJeEym8OyIYo0Ei9BtMMjU5Yb3APcjT1ipv14kxDiXVVM6ZNkPfJ9zz22FS7YbVg7aI69zqInb%2BfGMloKGjCF9oiJXBUvj2y7MGoL1F3tPNDCP7GlIKWThTfyYk1jI2YfT4%3D"&sign_type="RSA"&subject="JAJAHOME"&total_fee="0.01"
     */

    private DataBean data;
    /**
     * data : {"order_string":"_input_charset=\"utf-8\"&body=\"JAJAHOME付款 900 元\"&notify_url=\"http://115.159.102.231/furniture/call_back_zfb_pay.php\"&out_trade_no=\"733020531472202094\"&partner=\"2088121185691103\"&payment_type=\"1\"&seller_id=\"2930643693@qq.com\"&service=\"mobile.securitypay.pay\"&sign=\"doBcns4bfcaNmAF4k0GZ67vdhyIyj3%2BcgN3WJ3z9qJeEym8OyIYo0Ei9BtMMjU5Yb3APcjT1ipv14kxDiXVVM6ZNkPfJ9zz22FS7YbVg7aI69zqInb%2BfGMloKGjCF9oiJXBUvj2y7MGoL1F3tPNDCP7GlIKWThTfyYk1jI2YfT4%3D\"&sign_type=\"RSA\"&subject=\"JAJAHOME\"&total_fee=\"0.01\""}
     * cmd : pay_alipay
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
        private String order_string;

        public String getOrder_string() {
            return order_string;
        }

        public void setOrder_string(String order_string) {
            this.order_string = order_string;
        }
    }
}
