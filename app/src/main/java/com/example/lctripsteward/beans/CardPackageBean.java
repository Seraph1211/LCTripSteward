package com.example.lctripsteward.beans;

import java.util.List;

/**
 * 卡包，展示用户已拥有的卡券
 */
public class CardPackageBean {

    /**
     * result : {"coupon_bag":[{"count":0,"coupon_cost":12,"coupon_id":1,"coupon_name":"test1","coupon_type":0,"sill":2,"user_store_id":0,"user_type":0,"value":10},{"count":0,"coupon_cost":10,"coupon_id":2,"coupon_name":"test2","coupon_type":0,"sill":5,"user_store_id":0,"user_type":0,"value":10}],"page_total":1}
     * msg_code : 0000
     * msg_message : 处理成功
     */

    private ResultBean result;
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

    public String getMsg_message() {
        return msg_message;
    }

    public void setMsg_message(String msg_message) {
        this.msg_message = msg_message;
    }

    public static class ResultBean {
        /**
         * coupon_bag : [{"count":0,"coupon_cost":12,"coupon_id":1,"coupon_name":"test1","coupon_type":0,"sill":2,"user_store_id":0,"user_type":0,"value":10},{"count":0,"coupon_cost":10,"coupon_id":2,"coupon_name":"test2","coupon_type":0,"sill":5,"user_store_id":0,"user_type":0,"value":10}]
         * page_total : 1
         */

        private int page_total;
        private List<CouponBagBean> coupon_bag;

        public int getPage_total() {
            return page_total;
        }

        public void setPage_total(int page_total) {
            this.page_total = page_total;
        }

        public List<CouponBagBean> getCoupon_bag() {
            return coupon_bag;
        }

        public void setCoupon_bag(List<CouponBagBean> coupon_bag) {
            this.coupon_bag = coupon_bag;
        }

        public static class CouponBagBean {
            /**
             * count : 0
             * coupon_cost : 12
             * coupon_id : 1
             * coupon_name : test1
             * coupon_type : 0
             * sill : 2
             * user_store_id : 0
             * user_type : 0
             * value : 10
             */

            private int count;  //剩余数量
            private int coupon_cost;  //所需积分
            private int coupon_id;
            private String coupon_name;  //名称
            private int coupon_type;  //种类0~4
            private int sill;  //门槛
            private int user_store_id;  //对应的商店
            private int user_type;  //
            private int value;  //价值

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
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

            public int getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(int coupon_type) {
                this.coupon_type = coupon_type;
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
