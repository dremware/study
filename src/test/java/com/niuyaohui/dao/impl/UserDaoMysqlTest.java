package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.User;
import com.niuyaohui.dao.BaseUserDao;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class UserDaoMysqlTest {
    BaseUserDao dao = new UserDaoMysql();

    @Test
    public void console() {
        List<Map<String,Integer>> console = dao.console();
        System.out.println(console);
    }

    @Test
    public void findAll() {
        List<User> all = dao.findAll(true,0,2);
        System.out.println(all);
    }

    @Test
    public void findByUserPhone() {
        User user = dao.findByUserPhone("54454545454");
        System.out.println(user);
    }

    @Test
    public void findByIdCard() {
        User user = dao.findByIdCard("21212212121212121");
        System.out.println(user);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setUserName("欧文");
        user.setUserPhone("123123");
        user.setIdCard("1212555121211");
        user.setUserPwd("1215551");
        boolean flag = dao.insert(user);
        System.out.println(flag);
    }

    @Test
    public void update() {
        User user = new User();
        user.setUserName("欧文");
        user.setUserPhone("123123");
        user.setIdCard("2126666611");
        user.setUserPwd("1215551");
        boolean flag = dao.update(4,user);
        System.out.println(flag);
    }

    @Test
    public void delete() {
        System.out.println(dao.delete(1));
    }
}