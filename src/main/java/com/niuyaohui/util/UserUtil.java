package com.niuyaohui.util;

import javax.servlet.http.HttpSession;

public class UserUtil {
    /**
     * 根据session获取用户的姓名
     * @param session
     * @return
     */
    public static String getUserName(HttpSession session){
        return (String) session.getAttribute("adminUserName");
    }

    /**
     * 根据session获取用户的电话
     * @param session
     * @return
     */
    public static String getUserPhone(HttpSession session){
        // TODO : 还没有编写柜子端，未存储任何的录入人信息，无法获取录入人电话
        return "1888888888";
    }
}
