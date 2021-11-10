package com.niuyaohui.service;

import com.niuyaohui.bean.User;
import com.niuyaohui.dao.BaseUserDao;
import com.niuyaohui.dao.impl.UserDaoMysql;

import java.util.List;
import java.util.Map;

public class UserService {

    private static BaseUserDao dao = new UserDaoMysql();

    /**
     * 用于查询数据库中的全部用户人数，和用户日注册量
     *
     * @return [{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有用户
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 用户的集合
     */
    public static List<User> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit, offset, pageNumber);
    }

    /**
     * 根据手机号码，查询用户信息
     *
     * @param userPhone 用户手机号
     * @return 查询的用户信息，电话不存在时返回null
     */
    public static User findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据身份证号，查询用户信息
     *
     * @param idCard 身份证号
     * @return 查询的用户信息，身份证号不存在时返回null
     */
    public static User findByIdCard(String idCard) {
        return dao.findByIdCard(idCard);
    }

    /**
     * 用户信息的录入
     *
     * @param user 要录入的用户对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public static boolean insert(User user) {
        // 一个手机号码只能注册一个用户
        if(dao.findByUserPhone(user.getUserPhone()) != null){
            return false; // 该手机号已经注册过用户，不能再注册
        }
        return dao.insert(user);
    }

    /**
     * 根据id修改用户信息
     *
     * @param id      要修改的用户id
     * @param newUser 新的用户对象（userName, userPhone , idCard, userPwd）
     * @return 修改的结果 true表示成功，false表示失败
     */
    public static boolean update(int id, User newUser) {
        // 一个手机号码只能注册一个快递员
        if((dao.findByUserPhone(newUser.getUserPhone()) == null) || (dao.findByUserPhone(newUser.getUserPhone()).getId() == newUser.getId())  ){
            return dao.update(id,newUser);
        }
        return false; // 该手机号已经注册过快递员，不能修改手机号为已经注册过的手机号
    }

    /**
     * 根据id，删除单个用户信息
     *
     * @param id 要删除的用户id
     * @return 删除的结果 ， true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
