package ru.netology.data;

import lombok.Value;

public class DataHelper {
    @Value
    public static class AuthInfo{
        private String login;
        private String password;

    }
    public static AuthInfo getAuthInfo (){
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo (){

        return new AuthInfo("petya", "123qwert");
    }


    @Value
    public static class VerificationCode{
        private String code;
    }
}
