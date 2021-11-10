package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.Express;
import com.niuyaohui.dao.BaseExpressDao;
import com.niuyaohui.exception.DuplicateCodeException;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ExpressDaoMysqlTest {

    BaseExpressDao dao = new ExpressDaoMysql();
    @Test
    public void console() {
        List<Map<String,Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Express> all = dao.findAll(true,0,2);
        System.out.println(all);
    }

    @Test
    public void findByNumber() {
        Express e = dao.findByNumber("125");
        System.out.println(e);
    }

    @Test
    public void findByCode() {
        Express e = dao.findByCode("12377");
        System.out.println(e);
    }

    @Test
    public void findByUserPhone() {
        List<Express> es = dao.findByUserPhone("15201012256");
        System.out.println(es);
    }

    @Test
    public void findBySysPhone() {
        List<Express> es = dao.findBySysPhone("1888888888");
        System.out.println(es);
    }

    @Test
    public void insert() {
        //String number, String username, String userPhone, String company, String sysPhone
        Express e = new Express("123456","小三","15235265896","顺丰快递","18888888888","666666");
        boolean insert = false;
        try{
            insert = dao.insert(e); // 通过dao进行插入测试不会自动生成取件码，用service才可随机生成取件码
        }catch (DuplicateCodeException duplicateCodeException){
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);
    }

    @Test
    public void insert2() {

        //String number, String username, String userPhone, String company, String sysPhone
        boolean insert = false;
        try{
            for(int i=0; i<100; i++){
                Express e = new Express("123456"+i,"铁山靠","15235265896","顺丰快递","18888888888","666000"+i);
                insert = dao.insert(e);
            }

        }catch (DuplicateCodeException duplicateCodeException){
            System.out.println("取件码重复的异常被捕获到了");
        }
        System.out.println(insert);
    }

    @Test
    public void update() {
        Express e = new Express();
        e.setNumber("321");
        e.setCompany("嘿嘿快递");
        e.setUsername("王二");
        e.setStatus(1);
        boolean flag = dao.update(1,e);
        System.out.println(flag);
    }

    @Test
    public void updateStatus() {
        boolean flag = dao.updateStatus("12377");
        System.out.println(flag);
    }

    @Test
    public void delete() {
        boolean flag = dao.delete(1);
        System.out.println(flag);
    }
}