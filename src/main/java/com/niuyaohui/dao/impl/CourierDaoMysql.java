package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.Courier;
import com.niuyaohui.dao.BaseCourierDao;
import com.niuyaohui.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourierDaoMysql implements BaseCourierDao {

    // 查询语句写成大写字母是为了提高一丢丢效率，写成小写系统会自动转换成大写
    private static final String SQL_CONSOLE = "select " +
            "count(id) data3_size," +
            "count(to_days(REGTIME)=to_days(now()) or null) data3_day" +
            " FROM COURIER";
    // 用于查询数据库中的所有的快递员信息
    private static final String SQL_FIND_ALL = "SELECT * FROM COURIER";
    // 用于分页查询数据库中的快递员信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM COURIER LIMIT ?,?";
    // 通过手机号码查询快递员信息
    private static final String SQL_FIND_BY_EXPHONE = "SELECT * FROM COURIER WHERE EXPHONE=?";
    // 通过身份证号查询快递员信息
    private static final String SQL_FIND_BY_IDCARD = "SELECT * FROM COURIER WHERE IDCARD=?";
    // 录入快递员
    private static final String SQL_INSERT = "INSERT INTO COURIER(EXNAME,EXPHONE,IDCARD,EXPASSWORD,TRANNUMBER,REGTIME) VALUES(?,?,?,?,\"0\",NOW())";
    // 快递员修改
    private static final String SQL_UPDATE = "UPDATE COURIER SET EXNAME=?,EXPHONE=?,IDCARD=?,EXPASSWORD=? WHERE ID=?";
    // 快递员的删除
    private static final String SQL_DELETE = "DELETE FROM COURIER WHERE ID=?";




    /**
     * 用于查询数据库中的全部快递员人数，和快递员日注册量
     *
     * @return [{size:总数,day:新增}]
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
                int data3_size = rs.getInt("data3_size");
                int data3_day = rs.getInt("data3_day");
                Map data1 = new HashMap();
                data1.put("data3_size",data3_size);
                data1.put("data3_day",data3_day);
                data.add(data1);
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
     * 用于查询所有快递员
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 快递员的集合
     */
    @Override
    public List<Courier> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<Courier> data = new ArrayList<>();
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            if(limit){
                state = conn.prepareStatement(SQL_FIND_LIMIT);
                //3.    填充参数(可选)
                state.setInt(1,offset);
                state.setInt(2,pageNumber);
            }else{
                state = conn.prepareStatement(SQL_FIND_ALL);
            }
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            while (rs.next()){
                int id = rs.getInt("id");
                String exName = rs.getString("exName");
                String exPhone = rs.getString("exPhone");
                String idCard = rs.getString("idCard");
                String exPassWord = rs.getString("exPassWord");
                String tranNumber = rs.getString("tranNumber");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                Courier courier = new Courier(id,exName,exPhone,idCard,exPassWord,tranNumber,regTime,preLogTime);
                data.add(courier);
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
     * 根据手机号码，查询快递员信息
     *
     * @param exPhone 快递员手机号
     * @return 查询的快递信息，电话不存在时返回null
     */
    @Override
    public Courier findByExPhone(String exPhone) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_EXPHONE);
            //3.    填充参数(可选)
            state.setString(1,exPhone);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            if(rs.next()){
                int id = rs.getInt("id");
                String exName = rs.getString("exName");
                String idCard = rs.getString("idCard");
                String exPassWord = rs.getString("exPassWord");
                String tranNumber = rs.getString("tranNumber");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                Courier courier = new Courier(id,exName,exPhone,idCard,exPassWord,tranNumber,regTime,preLogTime);
                return courier;
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
     * 根据身份证号，查询快递员信息
     *
     * @param idCard 身份证号
     * @return 查询的快递信息，取件码不存在时返回null
     */
    @Override
    public Courier findByIdCard(String idCard) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_FIND_BY_IDCARD);
            //3.    填充参数(可选)
            state.setString(1,idCard);
            //4.    执行SQL语句
            rs = state.executeQuery();
            //5.    获取执行的结果
            if(rs.next()){
                int id = rs.getInt("id");
                String exName = rs.getString("exName");
                String exPhone = rs.getString("exPhone");
                String exPassWord = rs.getString("exPassWord");
                String tranNumber = rs.getString("tranNumber");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                Courier courier = new Courier(id,exName,exPhone,idCard,exPassWord,tranNumber,regTime,preLogTime);
                return courier;
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
     * 快递员信息的录入
     *
     * @param courier 要录入的快递员对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(Courier courier) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数(可选)
            state.setString(1,courier.getExName());
            state.setString(2,courier.getExPhone());
            state.setString(3,courier.getIdCard());
            state.setString(4,courier.getExPassword());
            //4.    执行SQL语句,并获取执行结果
            return state.executeUpdate()>0?true:false;
        }catch (SQLException e1){
            e1.printStackTrace();
        }finally {
            //5.    资源的释放
            DruidUtil.close(conn,state,rs);
        }
        return false;
    }

    /**
     * 根据id修改快递员信息
     *
     * @param id         要修改的快递员id
     * @param newCourier 新的快递员对象（exName, exPhone , idCard, exPassWord）
     * @return 修改的结果 true表示成功，false表示失败
     */
    @Override
    public boolean update(int id, Courier newCourier) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_UPDATE);
            //3.    填充参数(可选)
            state.setString(1,newCourier.getExName());
            state.setString(2,newCourier.getExPhone());
            state.setString(3,newCourier.getIdCard());
            state.setString(4,newCourier.getExPassword());
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
     * 根据id，删除单个快递员信息
     *
     * @param id 要删除的快递员id
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
