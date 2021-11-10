package com.niuyaohui.wx;

import com.niuyaohui.mvc.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController {

    @ResponseBody("/wx/loginSms.do")
    public String sendSms(HttpServletRequest req, HttpServletResponse resp){

        return "";
    }

    @ResponseBody("/wx/login.do")
    public String login(HttpServletRequest req, HttpServletResponse resp){

        return "";
    }
    @ResponseBody("/wx/logout.do")
    public String logout(HttpServletRequest req, HttpServletResponse resp){

        return "";
    }
}
