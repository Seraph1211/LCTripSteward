package com.example.lctripsteward.beans;

public class MonthReportBean {

    /**
     * result : {"CO2_reduction_last_month":537500,"CO2_reduction_this_month":2854500,"mileage_bike":10,"mileage_bus":10,"mileage_subway":50,"mileage_total":570,"mileage_walk":500,"user_rank_last_month":12,"user_rank_this_month":10}
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
         * CO2_reduction_last_month : 537500
         * CO2_reduction_this_month : 2854500
         * mileage_bike : 10
         * mileage_bus : 10
         * mileage_subway : 50
         * mileage_total : 570
         * mileage_walk : 500
         * user_rank_last_month : 12
         * user_rank_this_month : 10
         */

        private int CO2_reduction_last_month;
        private int CO2_reduction_this_month;
        private int mileage_bike;
        private int mileage_bus;
        private int mileage_subway;
        private int mileage_total;
        private int mileage_walk;
        private int user_rank_last_month;
        private int user_rank_this_month;

        public int getCO2_reduction_last_month() {
            return CO2_reduction_last_month;
        }

        public void setCO2_reduction_last_month(int CO2_reduction_last_month) {
            this.CO2_reduction_last_month = CO2_reduction_last_month;
        }

        public int getCO2_reduction_this_month() {
            return CO2_reduction_this_month;
        }

        public void setCO2_reduction_this_month(int CO2_reduction_this_month) {
            this.CO2_reduction_this_month = CO2_reduction_this_month;
        }

        public int getMileage_bike() {
            return mileage_bike;
        }

        public void setMileage_bike(int mileage_bike) {
            this.mileage_bike = mileage_bike;
        }

        public int getMileage_bus() {
            return mileage_bus;
        }

        public void setMileage_bus(int mileage_bus) {
            this.mileage_bus = mileage_bus;
        }

        public int getMileage_subway() {
            return mileage_subway;
        }

        public void setMileage_subway(int mileage_subway) {
            this.mileage_subway = mileage_subway;
        }

        public int getMileage_total() {
            return mileage_total;
        }

        public void setMileage_total(int mileage_total) {
            this.mileage_total = mileage_total;
        }

        public int getMileage_walk() {
            return mileage_walk;
        }

        public void setMileage_walk(int mileage_walk) {
            this.mileage_walk = mileage_walk;
        }

        public int getUser_rank_last_month() {
            return user_rank_last_month;
        }

        public void setUser_rank_last_month(int user_rank_last_month) {
            this.user_rank_last_month = user_rank_last_month;
        }

        public int getUser_rank_this_month() {
            return user_rank_this_month;
        }

        public void setUser_rank_this_month(int user_rank_this_month) {
            this.user_rank_this_month = user_rank_this_month;
        }
    }
}
