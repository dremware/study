package com.niuyaohui.util;


import org.junit.Test;

public class TencentSMSUtilTest {

    @Test
    public void sendSms(){
        boolean flag = TencentSMSUtil.send("15039449158","123456");
        System.out.println(flag);
    }

    @Test
    public void login(){
        boolean falg = TencentSMSUtil.loginSMS("15039449158","111111");
        System.out.println(falg);
    }

}