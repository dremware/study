package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.Express;
import com.niuyaohui.dao.BaseExpressDao;
import com.niuyaohui.exception.DuplicateCodeException;
import com.niuyaohui.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpressDaoMysql implements BaseExpressDao {

    // 查询语句写成大写字母是为了提高一丢丢效率，写成小写系统会自动转换成大写
    private static final String SQL_CONSOLE = "select " +
            "count(id) data1_size," +
            "count(to_days(intime)=to_days(now()) or null) data1_day," +
            "count(status=0 or null) data2_size," +
            "count(to_days(intime)=to_days(now()) and status=0 or null) data2_day" +
            " from express";
    // 用于查询数据库中的所有的快递信息
    private static final String SQL_FIND_ALL = "SELECT * FROM EXPRESS";
    // 用于分页查询数据库中的快递信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM EXPRESS LIMIT ?,?";
    // 通过取件码查询快递信息
    private static final String SQL_FIND_BY_CODE = "SELECT * FROM EXPRESS WHERE CODE=?";
    // 通过快递单号查询快递信息
    private static final String SQL_FIND_BY_NUMBER = "SELECT * FROM EXPRESS WHERE NUMBER=?";
    // 通过录入人的手机号码查询快递信息
    private static final String SQL_FIND_BY_SYSPHONE = "SELECT * FROM EXPRESS WHERE SYSPHONE=?";
    // 通过用户手机号码查询用户所有快递
    private static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM EXPRESS WHERE USERPHONE=?";
    // 录入快递
    private static final String SQL_INSERT = "INSERT INTO EXPRESS(NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)";
    // 快递修改
    private static final String SQL_UPDATE = "UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?";
    // 快递的状态码改变（取件）
    private static final String SQL_UPDATE_STATUS = "UPDATE EXPRESS SET STATUS=1,OUTTIME=NOW(),CODE=NULL WHERE CODE=?";
    // 快递的删除
    private static final String SQL_DELETE = "DELETE FROM EXPRESS WHERE ID=?";



    /**
     * 用于查询数据库中的全部快递（总数+新增），待取件快递（总数+新增）
     *
     * @return [{size:总数,day:新增},{size:总数,day:新增}]
     */
    @Override
    public List<Map<String, Integer>> console() {
        ArrayList<Map<String,Integer>> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_CONSOLE);
            //3.    填充参数(可选)
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            if(rs.next()){
                int data1_size = rs.getInt("data1_size");
                int data1_day = rs.getInt("data1_day");
                int data2_size = rs.getInt("data2_size");
                int data2_day = rs.getInt("data2_day");
                Map data1 = new HashMap();
                data1.put("data1_size",data1_size);
                data1.put("data1_day",data1_day);
                Map data2 = new HashMap();
                data2.put("data2_size",data2_size);
                data2.put("data2_day",data2_day);
                data.add(data1);
                data.add(data2);
            }else{
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return data;
    }

    /**
     * 用于查询所有快递
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递的集合
     */
    @Override
    public List<Express> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            if(limit){ // 如果是分页查询，则执行分页查询的sql语句
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数(可选)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else{ // 否则查询全部
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            while (rs.next()){
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
            }


        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return data;
    }

    /**
     * 根据快递单号，查询快递信息
     *
     * @param number 单号
     * @return 查询的快递信息，单号不存在时返回null
     */
    @Override
    public Express findByNumber(String number) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_NUMBER);
            //3.    填充参数(可选)
            state.setString(1,number);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            if(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return null;
    }

    /**
     * 根据取件码，查询快递信息
     *
     * @param code 取件码
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Express findByCode(String code) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_CODE);
            //3.    填充参数(可选)
            state.setString(1,code);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            if(rs.next()){
                int id = rs.getInt("id");
                String username = rs.getString("username");
                String number = rs.getString("number");
                String userPhone = rs.getString("userPhone");
                String company = rs.getString("company");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                return e;
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return null;
    }

    /**
     * 根据用户手机号码，查询他所有的快递信息
     *
     * @param userPhone 手机号码
     * @return 查询的快递信息列表，手机号码不存在时返回null
     */
    @Override
    public List<Express> findByUserPhone(String userPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_USERPHONE);
            //3.    填充参数(可选)
            state.setString(1,userPhone);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            while (rs.next()){
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String company = rs.getString("company");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                String sysPhone = rs.getString("sysPhone");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return data;
    }

    /**
     * 根据录入人手机号码，查询录入的所有记录
     *
     * @param sysPhone 手机号码
     * @return 查询的快递信息列表，手机号码不存在时返回null
     */
    @Override
    public List<Express> findBySysPhone(String sysPhone) {
        ArrayList<Express> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_SYSPHONE);
            //3.    填充参数(可选)
            state.setString(1,sysPhone);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            while (rs.next()){
                int id = rs.getInt("id");
                String number = rs.getString("number");
                String username = rs.getString("username");
                String company = rs.getString("company");
                String userPhone = rs.getString("userPhone");
                String code = rs.getString("code");
                Timestamp inTime = rs.getTimestamp("inTime");
                Timestamp outTime = rs.getTimestamp("outTime");
                int status = rs.getInt("status");
                Express e = new Express(id,number,username,userPhone,company,code,inTime,outTime,status,sysPhone);
                data.add(e);
            }

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return data;
    }

    /**
     * 快递的录入
     *INSERT INTO EXPRESS(NUMBER,USERNAME,USERPHONE,COMPANY,CODE,INTIME,STATUS,SYSPHONE) VALUES(?,?,?,?,?,NOW(),0,?)
     * @param e 要录入的快递对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(Express e) throws DuplicateCodeException {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数(可选)
            state.setString(1,e.getNumber());
            state.setString(2,e.getUsername());
            state.setString(3,e.getUserPhone());
            state.setString(4,e.getCompany());
            state.setString(5,e.getCode());
            state.setString(6,e.getSysPhone());
            //4.    执行SQL语句,并获取执行结果
            return state.executeUpdate()>0?true:false;
        }catch (SQLException e1){
            //throwables.printStackTrace();

            System.out.println(e1.getMessage());
            if(e1.getMessage().endsWith("for key 'code'")){
                // // 处理取件码相同时无法插入的异常问题,取件码重复
                DuplicateCodeException e2 = new DuplicateCodeException(e1.getMessage());
                throw e2;
            }else{
                e1.printStackTrace();
            }
        }finally {
            //5.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return false;
    }

    /**
     * UPDATE EXPRESS SET NUMBER=?,USERNAME=?,COMPANY=?,STATUS=? WHERE ID=?
     * @param id         要修改的快递id
     * @param newExpress 新的快递对象（number, company , username, userPhone）
     * @return
     */
    @Override
    public boolean update(int id, Express newExpress) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_UPDATE);
            //3.    填充参数(可选)
            state.setString(1,newExpress.getNumber());
            state.setString(2,newExpress.getUsername());
            state.setString(3,newExpress.getCompany());
            state.setInt(4,newExpress.getStatus());
            state.setInt(5,id);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return state.executeUpdate()>0?true:false;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 更改快递的状态为1 ， 表示取件完成
     *
     * @param code 要修改的快递单号
     * @return
     */
    @Override
    public boolean updateStatus(String code) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_UPDATE_STATUS);
            //3.    填充参数(可选)
            state.setString(1,code);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return state.executeUpdate()>0?true:false;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,null);
        }
        return false;
    }

    /**
     * 根据id，删除单个快递信息
     *
     * @param id 要删除的快递id
     * @return 删除的结果 ， true表示成功，false表示失败
     */
    @Override
    public boolean delete(int id) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_DELETE);
            //3.    填充参数(可选)
            state.setInt(1,id);
            //4.    执行SQL语句
            //5.    获取执行的结果
            return state.executeUpdate()>0?true:false;

        }catch (SQLException throwables){
            throwables.printStackTrace();
        }finally {
            //6.    资源的释放
            DruidUtil.close(conn,state,null);
        }
        return false;
    }
}
