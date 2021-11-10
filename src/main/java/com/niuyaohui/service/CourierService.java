package com.niuyaohui.service;

import com.niuyaohui.bean.Courier;
import com.niuyaohui.dao.BaseCourierDao;
import com.niuyaohui.dao.impl.CourierDaoMysql;

import java.util.List;
import java.util.Map;

public class CourierService {

    private static BaseCourierDao dao = new CourierDaoMysql();

    /**
     * 用于查询数据库中的全部快递员人数，和快递员日注册量
     *
     * @return [{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递员
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递员的集合
     */
    public static List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据手机号码，查询快递员信息
     *
     * @param exPhone 快递员手机号
     * @return 查询的快递信息，电话不存在时返回null
     */
    public static Courier findByExPhone(String exPhone) {
        return dao.findByExPhone(exPhone);
    }

    /**
     * 根据身份证号，查询快递员信息
     *
     * @param idCard 身份证号
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public static Courier findByIdCard(String idCard) {
        return dao.findByIdCard(idCard);
    }

    /**
     * 快递员信息的录入
     *
     * @param courier 要录入的快递员对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public static boolean insert(Courier courier) {
        // 一个手机号码只能注册一个快递员
        if(dao.findByExPhone(courier.getExPhone()) != null){
            return false; // 该手机号已经注册过快递员，不能再注册
        }
        return dao.insert(courier);
    }

    /**
     * 根据id修改快递员信息
     *
     * @param id         要修改的快递员id
     * @param newCourier 新的快递员对象（exName, exPhone , idCard, exPassWord）
     * @return 修改的结果 true表示成功，false表示失败
     */
    public static boolean update(int id, Courier newCourier) {
        // 一个手机号码只能注册一个快递员
        if((dao.findByExPhone(newCourier.getExPhone()) == null) || (dao.findByExPhone(newCourier.getExPhone()).getId() == newCourier.getId())  ){
            return dao.update(id,newCourier);
        }
        return false; // 该手机号已经注册过快递员，不能修改手机号为已经注册过的手机号
    }

    /**
     * 根据id，删除单个快递员信息
     *
     * @param id 要删除的快递员id
     * @return 删除的结果 ， true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
