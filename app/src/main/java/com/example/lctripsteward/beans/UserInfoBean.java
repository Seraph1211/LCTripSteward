package com.example.lctripsteward.beans;

import com.google.gson.annotations.SerializedName;

public class UserInfoBean {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("result")
    private UserInfoResultBean resultBean;
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

    public UserInfoResultBean getResultBean() {
        return resultBean;
    }

    public void setResultBean(UserInfoResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public static class UserInfoResultBean{
        @SerializedName("nickname")
        private String nickname;  //用户昵称
        @SerializedName("user_image_path")
        private String userImagePath;  //用户头像路径
        @SerializedName("city_id")
        private int cityId; //城市id
        @SerializedName("city_name")
        private String cityName;  //城市名称
        @SerializedName("user_level")
        private int userLevel;  //用户等级
        @SerializedName("sign_in_number")
        private int signInNumber;  //累计签到天数
        @SerializedName("carbon_credits_month")
        private int carbonCreditsMonth; //本月获取的碳积分
        @SerializedName("user_rank")
        private int userRank;  //本月个人碳积分所在城市排行
        @SerializedName("is_store")
        private int isStore;  //是否是商家 0-否，1-是
        @SerializedName("sign_in_today")
        private int signInToday;  //今日是否已签到 0-否，1-是{"result":{"carbon_credits_Total":100,"carbon_credits_today":0,"carbon_credits_unclaimed":0,"carbon_credits_useful":0,"mileage_bike_today":0,"mileage_bike_total":0,"mileage_bus_today":0,"mileage_bus_total":0,"mileage_subway_today":160,"mileage_subway_total":160,"mileage_walk_today":0,"mileage_walk_total":0},"msg_code":"0000","msg_message":"处理成功"}
        @SerializedName("team_id")
        private int teamId;  //-1表示没有加入任何队伍

        public int getTeamId(){
            return this.teamId;
        }

        public void setTeamId(int teamId){
            this.teamId = teamId;
        }

        public int getIsStore() {
            return isStore;
        }

        public void setIsStore(int isStore) {
            this.isStore = isStore;
        }

        public int getSignInToday() {
            return signInToday;
        }

        public void setSignInToday(int signInToday) {
            this.signInToday = signInToday;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getUserImagePath() {
            return userImagePath;
        }

        public void setUserImagePath(String userImagePath) {
            this.userImagePath = userImagePath;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public int getUserLevel() {
            return userLevel;
        }

        public void setUserLevel(int userLevel) {
            this.userLevel = userLevel;
        }

        public int getSignInNumber() {
            return signInNumber;
        }

        public void setSignInNumber(int signInNumber) {
            this.signInNumber = signInNumber;
        }

        public int getCarbonCreditsMonth() {
            return carbonCreditsMonth;
        }

        public void setCarbonCreditsMonth(int carbonCreditsMonth) {
            this.carbonCreditsMonth = carbonCreditsMonth;
        }

        public int getUserRank() {
            return userRank;
        }

        public void setUserRank(int userRank) {
            this.userRank = userRank;
        }
    }
}
