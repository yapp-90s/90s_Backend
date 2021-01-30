package com.yapp.ios2.dto;

import com.yapp.ios2.vo.User;
import lombok.*;

@Getter
@Setter
public class UserDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AccountInfo {
        private Long userUid;
        private String name;
        private String email;
        private String phoneNum;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PhoneNum{
        private String phoneNum;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor    public static class UserInfo{
        private Long uid;
        private String name;
        private String phoneNum;

        public UserInfo(User user){
            this.uid = user.getUid();
            this.name = user.getName();
            this.phoneNum = user.getPhoneNum();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserProfile{
        private Integer albumTotalCount;

        private UserInfo userInfo;

        public void setUserInfo(User user){
            this.userInfo = new UserInfo(user);
        }
    }

}
