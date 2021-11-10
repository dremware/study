package com.niuyaohui.service;

import com.niuyaohui.dao.BaseAdminDao;
import com.niuyaohui.dao.impl.AdminDaoMysql;

import java.util.Date;

public class AdminService {

    private static BaseAdminDao dao = new AdminDaoMysql();

    /**
     * 更新登录时间与ip
     * @param username
     * @param date
     * @param ip
     */
    public static void updateLoginTimeAndIP(String username, Date date, String ip){
        dao.updateLoginTime(username, date, ip);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return  true表示成功，false表示登录失败
     */
    public static boolean login(String username,String password){
        return dao.login(username,password);
    }

}
