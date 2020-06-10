package com.example.lctripsteward.bottomnavigation.home.rank;


import com.example.lctripsteward.R;

public class RankItem {
    private int ranking; //排名
    private int imageId; //头像
    private String userName; //姓名
    private int credits; //积分， 总积分或月度积分

    public RankItem(){
        this.imageId = R.drawable.girl;
        this.userName = "烽火戏诸侯";
        this.credits = 1564656;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}
