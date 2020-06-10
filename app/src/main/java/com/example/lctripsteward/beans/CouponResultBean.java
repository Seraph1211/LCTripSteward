package com.example.lctripsteward.beans;

import java.util.List;

/**
 * 商家首页(MerchantHomeFragment)，用于加载商家已发布的优惠券
 */
public class CouponResultBean {

    /**
     * result : {"coupon":[{"coupon__type":1,"coupon_cost":32,"coupon_id":3,"coupon_name":"test3","expiration_time":1580801535000,"sill":20,"user_store_id":0,"user_type":1,"value":10}]}
     * page_total : 1
     * * msg_code : 0000
     * msg_message : 处理成功
     */

    private ResultBean result;
    private int page_total;
    private String msg_code;
    private String msg_message;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getMsg_code() {
        return msg_code;
    }

    public void setMsg_code(String msg_code) {
        this.msg_code = msg_code;
    }

    public int getPage_total() {
        return page_total;
    }

    public void setPage_total(int page_total) {
        this.page_total = page_total;
    }

    public String getMsg_message() {
        return msg_message;
    }

    public void setMsg_message(String msg_message) {
        this.msg_message = msg_message;
    }

    public static class ResultBean {
        private List<CouponBean> coupon;

        public List<CouponBean> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public static class CouponBean {
            /**
             * coupon__type : 1
             *              * coupon_cost : 32
             *              * coupon_id : 3
             *              * coupon_name : test3
             *              * expiration_time : 1580801535000
             *              * sill : 20
             *              * user_store_id : 0
             *              * user_type : 1
             *              * value : 10
             */

            private int coupon__type;
            private int coupon_cost;
            private int coupon_id;
            private String coupon_name;
            private long expiration_time;
            private int sill;
            private int user_store_id;
            private int user_type;
            private int value;
            private int remaining;

            public void setRemaining(int remaining){
                this.remaining = remaining;
            }

            public int getRemaining(){
                return remaining;
            }

            public int getCoupon__type() {
                return coupon__type;
            }

            public void setCoupon__type(int coupon__type) {
                this.coupon__type = coupon__type;
            }

            public int getCoupon_cost() {
                return coupon_cost;
            }

            public void setCoupon_cost(int coupon_cost) {
                this.coupon_cost = coupon_cost;
            }

            public int getCoupon_id() {
                return coupon_id;
            }

            public void setCoupon_id(int coupon_id) {
                this.coupon_id = coupon_id;
            }

            public String getCoupon_name() {
                return coupon_name;
            }

            public void setCoupon_name(String coupon_name) {
                this.coupon_name = coupon_name;
            }

            public long getExpiration_time() {
                return expiration_time;
            }

            public void setExpiration_time(long expiration_time) {
                this.expiration_time = expiration_time;
            }

            public int getSill() {
                return sill;
            }

            public void setSill(int sill) {
                this.sill = sill;
            }

            public int getUser_store_id() {
                return user_store_id;
            }

            public void setUser_store_id(int user_store_id) {
                this.user_store_id = user_store_id;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }
        }
    }
}
