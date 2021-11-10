package com.niuyaohui.dao.impl;

import com.niuyaohui.dao.BaseAdminDao;
import com.niuyaohui.util.DruidUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class AdminDaoMysql implements BaseAdminDao {
    private static final String SQL_UPDATE_LOGIN_TIME = "UPDATE EADMIN SET LOGINTIME=?,LOGINIP=? WHERE username=?";
    private static final String SQL_LOGIN = "SELECT ID FROM EADMIN WHERE USERNAME=? AND PASSWORD=?";

    /**
     * 根据用户名，更新登录时间和登录ip
     *
     * @param username
     * @param date
     * @param ip
     */
    @Override
    public void updateLoginTime(String username, Date date, String ip) {
        //1. 获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2. 预编译SQL语句
        try{
            //3. 填充参数
            state = conn.prepareStatement(SQL_UPDATE_LOGIN_TIME);
            state.setDate(1,new java.sql.Date(date.getTime()));
            state.setString(2,ip);
            state.setString(3,username);
            //4. 执行SQL语句
            state.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //5. 释放资源
            DruidUtil.close(conn,state,null);
        }


    }

    /**
     * 管理员根据账号密码登录
     *
     * @param username 账号
     * @param password 密码
     * @return 登录的结果，true表示成功
     */
    @Override
    public boolean login(String username, String password) {
        //1. 获取连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2. 预编译SQL语句
        try{
            //3. 填充参数
            state = conn.prepareStatement(SQL_LOGIN);
            state.setString(1,username);
            state.setString(2,password);
            //4. 执行SQL语句,并获取结果
            rs = state.executeQuery();
            //5. 根据查询结果返回true或false
            return rs.next();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            //5. 释放资源
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
