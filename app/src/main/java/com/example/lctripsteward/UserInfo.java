package com.example.lctripsteward;

/**
 * 用户信息
 */
public class UserInfo {
    public static int  id;  //id，取手机号前10位
    public static String img;  //用户头像，用base64的方式储存
    public static String account;  //账号，即注册用的手机号
    public static String password = "";  //用户密码
    public static String nickname = "";  //昵称
}
