package com.niuyaohui.bean;

import java.sql.Timestamp;
import java.util.Objects;

/**
 * 快递员信息类
 */
public class Courier {
    private int id;
    private String exName;
    private String exPhone;
    private String idCard;
    private String exPassword;
    private String tranNumber;
    private Timestamp regTime;
    private Timestamp preLogTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Courier courier = (Courier) o;
        return id == courier.id && Objects.equals(exName, courier.exName) && Objects.equals(exPhone, courier.exPhone) && Objects.equals(idCard, courier.idCard) && Objects.equals(exPassword, courier.exPassword) && Objects.equals(tranNumber, courier.tranNumber) && Objects.equals(regTime, courier.regTime) && Objects.equals(preLogTime, courier.preLogTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, exName, exPhone, idCard, exPassword, tranNumber, regTime, preLogTime);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", exName='" + exName + '\'' +
                ", exPhone='" + exPhone + '\'' +
                ", idCard='" + idCard + '\'' +
                ", exPassword='" + exPassword + '\'' +
                ", tranNumber='" + tranNumber + '\'' +
                ", regTime=" + regTime +
                ", preLogTime=" + preLogTime +
                '}';
    }

    public Courier(String exName, String exPhone, String idCard, String exPassword) {
        this.exName = exName;
        this.exPhone = exPhone;
        this.idCard = idCard;
        this.exPassword = exPassword;
    }

    public Courier(int id, String exName, String exPhone, String idCard, String exPassword, String tranNumber, Timestamp regTime, Timestamp preLogTime) {
        this.id = id;
        this.exName = exName;
        this.exPhone = exPhone;
        this.idCard = idCard;
        this.exPassword = exPassword;
        this.tranNumber = tranNumber;
        this.regTime = regTime;
        this.preLogTime = preLogTime;
    }

    public Courier() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getExPhone() {
        return exPhone;
    }

    public void setExPhone(String exPhone) {
        this.exPhone = exPhone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getExPassword() {
        return exPassword;
    }

    public void setExPassword(String exPassword) {
        this.exPassword = exPassword;
    }

    public String getTranNumber() {
        return tranNumber;
    }

    public void setTranNumber(String tranNumber) {
        this.tranNumber = tranNumber;
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
}
