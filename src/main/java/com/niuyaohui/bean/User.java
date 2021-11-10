package com.niuyaohui.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 用户信息类
 */
public class User {
    private int id;
    private String userName;
    private String userPhone;
    private String idCard;
    private String userPwd;
    private Timestamp regTime;
    private Timestamp preLogTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userPwd='" + userPwd + '\'' +
                ", regTime=" + regTime +
                ", preLogTime=" + preLogTime +
                '}';
    }

    public User(String userName, String userPhone, String idCard, String userPwd) {
        this.userName = userName;
        this.userPhone = userPhone;
        this.idCard = idCard;
        this.userPwd = userPwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true;}
        if (o == null || getClass() != o.getClass()){ return false;}
        User user = (User) o;
        return id == user.id && Objects.equals(userName, user.userName) && Objects.equals(userPhone, user.userPhone) && Objects.equals(idCard, user.idCard) && Objects.equals(userPwd, user.userPwd) && Objects.equals(regTime, user.regTime) && Objects.equals(preLogTime, user.preLogTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, userPhone, idCard, userPwd, regTime, preLogTime);
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Timestamp getRegTime() {
        return regTime;
    }

    public void setRegTime(Timestamp regTime) {
        this.regTime = regTime;
    }

    public Timestamp getPreLogTime() {
        return preLogTime;
    }

    public void setPreLogTime(Timestamp preLogTime) {
        this.preLogTime = preLogTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User(int id, String userName, String userPhone, String idCard, String userPwd, Timestamp regTime, Timestamp preLogTime) {
        this.id = id;
        this.userName = userName;
        this.userPhone = userPhone;
        this.idCard = idCard;
        this.userPwd = userPwd;
        this.regTime = regTime;
        this.preLogTime = preLogTime;
    }
}
