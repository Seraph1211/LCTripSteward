package com.example.lctripsteward.beans;

import com.google.gson.annotations.SerializedName;

public class CarbonCreditsInfoBean {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("result")
    private CarbonCreditsInfoResultBean resultBean;

    private String msg_code;
    private String msg_message;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public CarbonCreditsInfoResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(CarbonCreditsInfoResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public static class CarbonCreditsInfoResultBean {
        @SerializedName("carbon_credits_total")
        private int carbonCreditsTotal; //总碳积分
        @SerializedName("carbon_credits_today")
        private int carbonCreditsToday; //待领取的碳积分
        @SerializedName("carbon_credits_useful")
        private int carbonCreditsAvailable; //可用碳积分
        @SerializedName("mileage_subway_today")
        private int mileageSubwayToday;  //今日地铁里程
        @SerializedName("mileage_subway_total")
        private int mileageSubwayTotal;  //总地铁里程
        @SerializedName("mileage_bus_today")
        private int mileageBusToday;  //今日公交里程
        @SerializedName("mileage_bus_total")
        private int mileageBusTotal;  //总公交里程
        @SerializedName("mileage_walk_today")
        private int mileageWalkToday;  //今日步行里程
        @SerializedName("mileage_walk_total")
        private int mileageWalkTotal;  //总步行里程
        @SerializedName("mileage_bike_today")
        private int mileageBikeToday;  //今日骑行里程
        @SerializedName("mileage_bike_total")
        private int mileageBikeTotal;  //总骑行里程

        public int getCarbonCreditsTotal() {
            return carbonCreditsTotal;
        }

        public void setCarbonCreditsTotal(int carbonCreditsTotal) {
            this.carbonCreditsTotal = carbonCreditsTotal;
        }

        public int getCarbonCreditsToday() {
            return carbonCreditsToday;
        }

        public void setCarbonCreditsToday(int carbonCreditsToday) {
            this.carbonCreditsToday = carbonCreditsToday;
        }

        public int getCarbonCreditsAvailable() {
            return carbonCreditsAvailable;
        }

        public void setCarbonCreditsAvailable(int carbonCreditsAvailable) {
            this.carbonCreditsAvailable = carbonCreditsAvailable;
        }

        public int getMileageSubwayToday() {
            return mileageSubwayToday;
        }

        public void setMileageSubwayToday(int mileageSubwayToday) {
            this.mileageSubwayToday = mileageSubwayToday;
        }

        public int getMileageSubwayTotal() {
            return mileageSubwayTotal;
        }

        public void setMileageSubwayTotal(int mileageSubwayTotal) {
            this.mileageSubwayTotal = mileageSubwayTotal;
        }

        public int getMileageBusToday() {
            return mileageBusToday;
        }

        public void setMileageBusToday(int mileageBusToday) {
            this.mileageBusToday = mileageBusToday;
        }

        public int getMileageBusTotal() {
            return mileageBusTotal;
        }

        public void setMileageBusTotal(int mileageBusTotal) {
            this.mileageBusTotal = mileageBusTotal;
        }

        public int getMileageWalkToday() {
            return mileageWalkToday;
        }

        public void setMileageWalkToday(int mileageWalkToday) {
            this.mileageWalkToday = mileageWalkToday;
        }

        public int getMileageWalkTotal() {
            return mileageWalkTotal;
        }

        public void setMileageWalkTotal(int mileageWalkTotal) {
            this.mileageWalkTotal = mileageWalkTotal;
        }

        public int getMileageBikeToday() {
            return mileageBikeToday;
        }

        public void setMileageBikeToday(int mileageBikeToday) {
            this.mileageBikeToday = mileageBikeToday;
        }

        public int getMileageBikeTotal() {
            return mileageBikeTotal;
        }

        public void setMileageBikeTotal(int mileageBikeTotal) {
            this.mileageBikeTotal = mileageBikeTotal;
        }
    }
}
