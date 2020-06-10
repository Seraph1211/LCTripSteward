package com.example.lctripsteward.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommodityBean implements Serializable {
    private CommodityResultBean commodityResultBean;
    private Discount discount;  //商品可用优惠

    public CommodityBean(){
        commodityResultBean = new CommodityResultBean();
        discount = new Discount();
    }

    public CommodityResultBean getCommodityResultBean() {
        return commodityResultBean;
    }

    public void setCommodityResultBean(CommodityResultBean commodityResultBean) {
        this.commodityResultBean = commodityResultBean;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public static class CommodityResultBean implements Serializable {

        @SerializedName("commodity_id")
        private int commodityId;  //商品id

        @SerializedName("commodity_picture")
        private int commodityPicture;  //商品图片路径

        @SerializedName("commodity_name")
        private String commodityName;  //商品名称

        @SerializedName("commodity_introduce")
        private String commodityIntroduce;  //商品介绍

        @SerializedName("commodity_price_original")
        private int commodityPriceOriginal;  //商品原价

        @SerializedName("commodity_price")
        private int commodityPrice;  //商品现价


        @SerializedName("discount_useful")
        private int discountUseful;  //可用优惠类型

        private int remaining;  //剩余数量

        private int carbonCreditsNeeds;  //所需碳积分

        public int getCarbonCreditsNeeds() {
            return carbonCreditsNeeds;
        }

        public void setCarbonCreditsNeeds(int carbonCreditsNeeds) {
            this.carbonCreditsNeeds = carbonCreditsNeeds;
        }

        public int getRemaining() {
            return remaining;
        }

        public void setRemaining(int remaining) {
            this.remaining = remaining;
        }

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public int getCommodityPicture() {
            return commodityPicture;
        }

        public void setCommodityPicture(int commodityPicture) {
            this.commodityPicture = commodityPicture;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getCommodityIntroduce() {
            return commodityIntroduce;
        }

        public void setCommodityIntroduce(String commodityIntroduce) {
            this.commodityIntroduce = commodityIntroduce;
        }

        public int getCommodityPriceOriginal() {
            return commodityPriceOriginal;
        }

        public void setCommodityPriceOriginal(int commodityPriceOriginal) {
            this.commodityPriceOriginal = commodityPriceOriginal;
        }

        public int getCommodityPrice() {
            return commodityPrice;
        }

        public void setCommodityPrice(int commodityPrice) {
            this.commodityPrice = commodityPrice;
        }

        public int getDiscountUseful() {
            return discountUseful;
        }

        public void setDiscountUseful(int discountUseful) {
            this.discountUseful = discountUseful;
        }
    }

    public static class Discount implements Serializable {

        @SerializedName("type")
        private int type;  //可用优惠id

        @SerializedName("sill")
        private int sill;  //使用门槛

        @SerializedName("value")
        private int value;  //优惠价值

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getSill() {
            return sill;
        }

        public void setSill(int sill) {
            this.sill = sill;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

}
