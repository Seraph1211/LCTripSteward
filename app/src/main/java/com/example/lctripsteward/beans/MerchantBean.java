package com.example.lctripsteward.beans;

import java.io.Serializable;

public class MerchantBean implements Serializable {

    /**
     * merchantPhoneNumber : 11111111111
     * merchantImage : werwer
     * merchantEmail : 235345476@qq.com
     * merchantAddress : wrwer
     * merchantIntroduce : werwer
     * merchantName : zyx
     */

    private int userId;
    private String merchantPhoneNumber;
    private String merchantImage;
    private String merchantEmail;
    private String merchantAddress;
    private String merchantIntroduce;
    private String merchantName;

    public String getMerchantPhoneNumber() {
        return merchantPhoneNumber;
    }

    public void setMerchantPhoneNumber(String merchantPhoneNumber) {
        this.merchantPhoneNumber = merchantPhoneNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMerchantImage() {
        return merchantImage;
    }

    public void setMerchantImage(String merchantImage) {
        this.merchantImage = merchantImage;
    }

    public String getMerchantEmail() {
        return merchantEmail;
    }

    public void setMerchantEmail(String merchantEmail) {
        this.merchantEmail = merchantEmail;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantIntroduce() {
        return merchantIntroduce;
    }

    public void setMerchantIntroduce(String merchantIntroduce) {
        this.merchantIntroduce = merchantIntroduce;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
