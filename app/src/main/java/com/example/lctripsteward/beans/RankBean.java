package com.example.lctripsteward.beans;

import java.util.List;

public class RankBean {

    /**
     * result : {"user_list":[{"carbonCredits":0,"nickname":"小明","userImagePath":"","userRank":1}]}
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
        private List<UserListBean> user_list;

        public List<UserListBean> getUser_list() {
            return user_list;
        }

        public void setUser_list(List<UserListBean> user_list) {
            this.user_list = user_list;
        }

        public static class UserListBean {
            /**
             * carbonCredits : 0
             * nickname : 小明
             * userImagePath :
             * userRank : 1
             */

            private int carbonCredits;
            private String nickname;
            private int userImagePath;
            private int userRank;

            public int getCarbonCredits() {
                return carbonCredits;
            }

            public void setCarbonCredits(int carbonCredits) {
                this.carbonCredits = carbonCredits;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getUserImagePath() {
                return userImagePath;
            }

            public void setUserImagePath(int userImagePath) {
                this.userImagePath = userImagePath;
            }

            public int getUserRank() {
                return userRank;
            }

            public void setUserRank(int userRank) {
                this.userRank = userRank;
            }
        }
    }
}
