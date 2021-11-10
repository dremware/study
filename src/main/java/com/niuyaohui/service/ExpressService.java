package com.niuyaohui.service;

import com.niuyaohui.bean.Express;
import com.niuyaohui.dao.BaseExpressDao;
import com.niuyaohui.dao.impl.ExpressDaoMysql;
import com.niuyaohui.exception.DuplicateCodeException;
import com.niuyaohui.util.RandomUtil;
import com.niuyaohui.util.TencentSMSUtil;

import java.util.List;
import java.util.Map;

public class ExpressService {
    //设计该service是为了通过下面这行代码更好的更换数据库，new使用ORACLE的数据库类即可，其他不需要改变
    private static  BaseExpressDao dao = new ExpressDaoMysql();

    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    public static List<Map<String, Integer>> console() {
        return dao.console();
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    public static List<Express> findAll(boolean limit, int offset, int pageNumber) {
        return dao.findAll(limit,offset,pageNumber);
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    public static Express findByNumber(String number) {
        return dao.findByNumber(number);
    }

    /**
     * 根据取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    public static Express findByCode(String code) {
        return dao.findByCode(code);
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表，手机号码不存在时返回null
     */
    public static List<Express> findByUserPhone(String userPhone) {
        return dao.findByUserPhone(userPhone);
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表，手机号码不存在时返回null
     */
    public static List<Express> findBySysPhone(String sysPhone) {
        return dao.findBySysPhone(sysPhone);
    }

    /**
     * 快递的录入
     *
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    public static boolean insert(Express e) {
        // 通过RandomUtil类随机生成取件码
        e.setCode(RandomUtil.getCode()+"");
        try{
            boolean flag = dao.insert(e);
            if(flag){
                //录入成功,发送短信
                TencentSMSUtil.send(e.getUserPhone(),e.getCode());
            }
            // 有可能发生取件码重复的异常
            return flag;
        }catch (DuplicateCodeException duplicateCodeException){
            return insert(e); // 发生取件码重复，重新调用该方法随机生成取件码再次插入，使用了递归
        }
    }

    /**
     * 快递的修改
     *
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number, company , username, userPhone）
     * @return  修改的结果，true表示成功，false表示失败
     *
     * 逻辑  BUG ,
     */
    public static boolean update(int id, Express newExpress) {
        if(newExpress.getUserPhone()!=null){
            dao.delete(id);
            return insert(newExpress);
        }else{
            boolean update = dao.update(id,newExpress);
            Express e = dao.findByNumber(newExpress.getNumber());
            if(newExpress.getStatus() == 1){
                updateStatus(e.getCode());
            }
            return update;
        }
    }

    /**
     * 更改快递的状态为1 ， 表示取件完成
     *
     * @param code 要修改的快递单号
     * @return
     */
    public static boolean updateStatus(String code) {
        return dao.updateStatus(code);
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果 ， true表示成功，false表示失败
     */
    public static boolean delete(int id) {
        return dao.delete(id);
    }
}
