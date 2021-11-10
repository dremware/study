package com.niuyaohui.controller;

import com.niuyaohui.bean.*;
import com.niuyaohui.mvc.ResponseBody;
import com.niuyaohui.service.CourierService;
import com.niuyaohui.service.UserService;
import com.niuyaohui.util.DateFormatUtil;
import com.niuyaohui.util.JSONUtil;
import com.tencentcloudapi.ckafka.v20190819.models.UserResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserController {

    public String console(HttpServletRequest request, HttpServletResponse response){
        List<Map<String, Integer>> data = CourierService.console();
        Message msg = new Message();
        if(data.size() == 0){
            msg.setStatus(-1);
        }else{
            msg.setStatus(0);
        }
        msg.setData(data);
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/list.do")
    public String list(HttpServletRequest request,HttpServletResponse response){

        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        List<User> list = UserService.findAll(true,offset,pageNumber);
        List<BootStrapTableUser> list2 = new ArrayList<>();
        for(User user:list){
            String regTime = DateFormatUtil.format(user.getRegTime());
            String preLogTime = user.getPreLogTime()==null?"":DateFormatUtil.format(user.getPreLogTime());
            BootStrapTableUser e2 = new BootStrapTableUser(user.getId(),user.getUserName(),user.getUserPhone(),user.getIdCard(), user.getUserPwd(), regTime,preLogTime);
            list2.add(e2);
        }
        List<Map<String,Integer>> console = UserService.console();
        Integer total = console.get(0).get("data4_size");
        //4.    将集合封装为  bootstrap-table识别的格式
        ResultData<BootStrapTableUser> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/user/insert.do")
    public String insert(HttpServletRequest request,HttpServletResponse response){
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String idCard = request.getParameter("idCard");
        String userPwd = request.getParameter("userPwd");
        User user = new User(userName,userPhone,idCard,userPwd);
        System.out.println(user);
        boolean flag = UserService.insert(user);
        Message msg = new Message();
        if(flag){
            //录入成功
            msg.setStatus(0);
            msg.setResult("用户录入成功！");
        }else{
            //录入失败
            msg.setStatus(-1);
            msg.setResult("用户录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        System.out.println("msg--->JSON="+json);
        return json;
    }

    @ResponseBody("/user/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String userPhone = request.getParameter("userPhone");
        User user = UserService.findByUserPhone(userPhone);
        Message msg = new Message();
        if(user == null){
            msg.setStatus(-1);
            msg.setResult("不存在该手机号注册的用户信息");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(user);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/user/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String idCard = request.getParameter("idCard");
        String userPwd = request.getParameter("userPwd");
        User newUser = new User();
        newUser.setId(id);
        newUser.setUserName(userName);
        newUser.setUserPhone(userPhone);
        newUser.setIdCard(idCard);
        newUser.setUserPwd(userPwd);
        System.out.println("id="+id+newUser);
        boolean flag = UserService.update(id, newUser);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("修改成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("修改失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }


    @ResponseBody("/user/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        System.out.println(id);
        boolean flag = UserService.delete(id);
        Message msg = new Message();
        if(flag){
            msg.setStatus(0);
            msg.setResult("删除成功");
        }else{
            msg.setStatus(-1);
            msg.setResult("删除失败");
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }
}
