package com.niuyaohui.dao.impl;

import com.niuyaohui.bean.User;
import com.niuyaohui.dao.BaseUserDao;
import com.niuyaohui.util.DruidUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDaoMysql implements BaseUserDao {

    // 查询语句写成大写字母是为了提高一丢丢效率，写成小写系统会自动转换成大写
    private static final String SQL_CONSOLE = "select " +
            "count(id) data4_size," +
            "count(to_days(REGTIME)=to_days(now()) or null) data4_day" +
            " FROM USER";
    // 用于查询数据库中的所有的用户信息
    private static final String SQL_FIND_ALL = "SELECT * FROM USER";
    // 用于分页查询数据库中的用户信息
    private static final String SQL_FIND_LIMIT = "SELECT * FROM USER LIMIT ?,?";
    // 通过手机号码查询用户信息
    private static final String SQL_FIND_BY_USERPHONE = "SELECT * FROM USER WHERE USERPHONE=?";
    // 通过身份证号查询用户信息
    private static final String SQL_FIND_BY_IDCARD = "SELECT * FROM USER WHERE IDCARD=?";
    // 录入用户
    private static final String SQL_INSERT = "INSERT INTO USER(USERNAME,USERPHONE,IDCARD,USERPWD,REGTIME) VALUES(?,?,?,?,NOW())";
    // 用户修改
    private static final String SQL_UPDATE = "UPDATE USER SET USERNAME=?,USERPHONE=?,IDCARD=?,USERPWD=? WHERE ID=?";
    // 用户的删除
    private static final String SQL_DELETE = "DELETE FROM USER WHERE ID=?";




    /**
     * 用于查询数据库中的全部用户人数，和用户日注册量
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
                int data4_size = rs.getInt("data4_size");
                int data4_day = rs.getInt("data4_day");
                Map data1 = new HashMap();
                data1.put("data4_size",data4_size);
                data1.put("data4_day",data4_day);
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
     * 用于查询所有用户
     *
     * @param limit      是否分页的标记，true表示分页。false表示查询所有
     * @param offset     SQL语句的起始索引
     * @param pageNumber 每一页查询的数量
     * @return 用户的集合
     */
    @Override
    public List<User> findAll(boolean limit, int offset, int pageNumber) {
        ArrayList<User> data = new ArrayList<>();
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
                String userName = rs.getString("userName");
                String userPhone = rs.getString("userPhone");
                String idCard = rs.getString("idCard");
                String userPwd = rs.getString("userPwd");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                User user = new User(id,userName,userPhone,idCard,userPwd,regTime,preLogTime);
                data.add(user);
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
     * 根据手机号码，查询用户信息
     *
     * @param userPhone 用户手机号
     * @return 查询的用户信息，电话不存在时返回null
     */
    @Override
    public User findByUserPhone(String userPhone) {
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
            if(rs.next()){
                int id = rs.getInt("id");
                String userName = rs.getString("userName");
                String idCard = rs.getString("idCard");
                String userPwd = rs.getString("userPwd");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                User user = new User(id,userName,userPhone,idCard,userPwd,regTime,preLogTime);
                return user;
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
     * 根据身份证号，查询用户信息
     *
     * @param idCard 身份证号
     * @return 查询的用户信息，身份证号不存在时返回null
     */
    @Override
    public User findByIdCard(String idCard) {
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
                String userName = rs.getString("userName");
                String userPhone = rs.getString("userPhone");
                String userPwd = rs.getString("userPwd");
                Timestamp regTime = rs.getTimestamp("regTime");
                Timestamp preLogTime = rs.getTimestamp("preLogTime");
                User user = new User(id,userName,userPhone,idCard,userPwd,regTime,preLogTime);
                return user;
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
     * 用户信息的录入
     *
     * @param user 要录入的用户对象
     * @return 录入的结果，true表示成功，false表示失败
     */
    @Override
    public boolean insert(User user) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        ResultSet rs = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_INSERT);
            //3.    填充参数(可选)
            state.setString(1, user.getUserName());
            state.setString(2, user.getUserPhone());
            state.setString(3, user.getIdCard());
            state.setString(4, user.getUserPwd());
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
     * 根据id修改用户信息
     *
     * @param id         要修改的用户id
     * @param newUser 新的用户对象（userName, userPhone , idCard, userPwd）
     * @return 修改的结果 true表示成功，false表示失败
     */
    @Override
    public boolean update(int id, User newUser) {
        //1.    获取数据库的连接
        Connection conn = DruidUtil.getConnection();
        PreparedStatement state = null;
        //2.    预编译SQL语句
        try{
            state = conn.prepareStatement(SQL_UPDATE);
            //3.    填充参数(可选)
            state.setString(1,newUser.getUserName());
            state.setString(2,newUser.getUserPhone());
            state.setString(3,newUser.getIdCard());
            state.setString(4,newUser.getUserPwd());
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
     * 根据id，删除单个用户信息
     *
     * @param id 要删除的用户id
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
