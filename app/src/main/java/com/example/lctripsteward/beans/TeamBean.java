package com.example.lctripsteward.beans;

import java.util.List;

public class TeamBean {

    /**
     * result : {"teamCarbonCredits":33300,"teamId":1,"teamLeaderId":1,"teamName":"catLovers","teamRank":1,"userList":[{"nickname":"小明","userCarbonCredits":0,"userId":1,"userLevel":3,"userRank":1}]}
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
         * teamCarbonCredits : 33300
         * teamId : 1
         * teamLeaderId : 1
         * teamName : catLovers
         * teamRank : 1
         * userList : [{"nickname":"小明","userCarbonCredits":0,"userId":1,"userLevel":3,"userRank":1}]
         */

        private int teamCarbonCredits;
        private int teamId;
        private int teamLeaderId;
        private String teamName;
        private int teamRank;
        private List<UserListBean> userList;

        public int getTeamCarbonCredits() {
            return teamCarbonCredits;
        }

        public void setTeamCarbonCredits(int teamCarbonCredits) {
            this.teamCarbonCredits = teamCarbonCredits;
        }

        public int getTeamId() {
            return teamId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public int getTeamLeaderId() {
            return teamLeaderId;
        }

        public void setTeamLeaderId(int teamLeaderId) {
            this.teamLeaderId = teamLeaderId;
        }

        public String getTeamName() {
            return teamName;
        }

        public void setTeamName(String teamName) {
            this.teamName = teamName;
        }

        public int getTeamRank() {
            return teamRank;
        }

        public void setTeamRank(int teamRank) {
            this.teamRank = teamRank;
        }

        public List<UserListBean> getUserList() {
            return userList;
        }

        public void setUserList(List<UserListBean> userList) {
            this.userList = userList;
        }

        public static class UserListBean {
            /**
             * nickname : 小明
             * userCarbonCredits : 0
             * userId : 1
             * userLevel : 3
             * userRank : 1
             */

            private String nickname;  //昵称
            private int userCarbonCredits;  //碳积分
            private int userId;  //id
            private int userLevel;  //等级
            private int userRank;  //排行
            private int img;

            public int getImg() {
                return img;
            }

            public void setImg(int img) {
                this.img = img;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getUserCarbonCredits() {
                return userCarbonCredits;
            }

            public void setUserCarbonCredits(int userCarbonCredits) {
                this.userCarbonCredits = userCarbonCredits;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getUserLevel() {
                return userLevel;
            }

            public void setUserLevel(int userLevel) {
                this.userLevel = userLevel;
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