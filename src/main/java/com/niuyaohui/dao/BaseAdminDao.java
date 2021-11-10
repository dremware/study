package com.niuyaohui.dao;

import java.util.Date;

/**
 * 用于定义接口eadmin表格的操作规范
 */
public interface BaseAdminDao {

    /**
     * 根据用户名，更新登录时间和登录ip
     * @param username
     */
    void updateLoginTime(String username, Date date, String ip);

    /**
     * 管理员根据账号密码登录
     * @param username  账号
     * @param password  密码
     * @return  登录的结果，true表示成功
     */
    boolean login(String username,String password);
}
