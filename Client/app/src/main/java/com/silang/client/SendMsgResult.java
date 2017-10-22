package com.silang.client;

import java.util.List;

/**
 * Created by Administrator on 2017/10/16.
 */

public class SendMsgResult {


    /**
     * total_count : 2
     * total_fee : 0.2000
     * unit : RMB
     * data : [{"code":0,"msg":"发送成功","count":1,"fee":0.05,"unit":"RMB","mobile":"13000000000","sid":3310228964},{"code":0,"msg":"发送成功","count":1,"fee":0.05,"unit":"RMB","mobile":"13000000001","sid":3310228968}]
     */

    private int total_count;
    private String total_fee;
    private String unit;
    private List<DataBean> data;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * code : 0
         * msg : 发送成功
         * count : 1
         * fee : 0.05
         * unit : RMB
         * mobile : 13000000000
         * sid : 3310228964
         */

        private int code;
        private String msg;
        private int count;
        private double fee;
        private String unit;
        private String mobile;
        private long sid;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getFee() {
            return fee;
        }

        public void setFee(double fee) {
            this.fee = fee;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public long getSid() {
            return sid;
        }

        public void setSid(long sid) {
            this.sid = sid;
        }
    }
}
