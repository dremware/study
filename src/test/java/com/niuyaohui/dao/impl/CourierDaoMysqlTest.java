package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.Courier;
import com.niuyaohui.dao.BaseCourierDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class CourierDaoMysqlTest {

    BaseCourierDao dao = new CourierDaoMysql();

    @Test
    public void console() {
        List<Map<String,Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<Courier> all = dao.findAll(true,0,2);
        System.out.println(all);
    }

    @Test
    public void findByExPhone() {
        Courier courier = dao.findByExPhone("11111111111");
        System.out.println(courier);
    }

    @Test
    public void findByIdCard() {
        Courier courier = dao.findByIdCard("777777777777777777");
        System.out.println(courier);
    }

    @Test
    public void insert() {

    }

    @Test
    public void update() {
        Courier courier = new Courier();
        courier.setExName("欧文");
        courier.setExPhone("123123");
        courier.setIdCard("1212555121211");
        courier.setExPassword("1215551");
        boolean flag = dao.update(6,courier);
        System.out.println(flag);

    }

    @Test
    public void delete() {
    }
}