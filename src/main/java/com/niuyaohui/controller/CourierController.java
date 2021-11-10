package com.niuyaohui.controller;

import com.niuyaohui.bean.*;
import com.niuyaohui.dao.BaseCourierDao;
import com.niuyaohui.mvc.ResponseBody;
import com.niuyaohui.service.CourierService;
import com.niuyaohui.service.ExpressService;
import com.niuyaohui.util.DateFormatUtil;
import com.niuyaohui.util.JSONUtil;
import com.niuyaohui.util.UserUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourierController {

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

    @ResponseBody("/courier/list.do")
    public String list(HttpServletRequest request,HttpServletResponse response){

        //1.    获取查询数据的起始索引值
        int offset = Integer.parseInt(request.getParameter("offset"));
        //2.    获取当前页要查询的数据量
        int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        //3.    进行查询
        List<Courier> list = CourierService.findAll(true,offset,pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        for(Courier courier:list){
            String regTime = DateFormatUtil.format(courier.getRegTime());
            String preLogTime = courier.getPreLogTime()==null?"":DateFormatUtil.format(courier.getPreLogTime());
            BootStrapTableCourier e2 = new BootStrapTableCourier(courier.getId(),courier.getExName(),courier.getExPhone(),courier.getIdCard(),courier.getExPassword(),courier.getTranNumber(),regTime,preLogTime);
            list2.add(e2);
        }
        List<Map<String,Integer>> console = CourierService.console();
        Integer total = console.get(0).get("data3_size");
        //4.    将集合封装为  bootstrap-table识别的格式
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        data.setTotal(total);
        String json = JSONUtil.toJSON(data);
        return json;
    }

    @ResponseBody("/courier/insert.do")
    public String insert(HttpServletRequest request,HttpServletResponse response){
        String exName = request.getParameter("exName");
        String exPhone = request.getParameter("exPhone");
        String idCard = request.getParameter("idCard");
        String exPassWord = request.getParameter("exPassWord");
        Courier courier = new Courier(exName,exPhone,idCard,exPassWord);
        System.out.println(courier);
        boolean flag = CourierService.insert(courier);
        Message msg = new Message();
        if(flag){
            //录入成功
            msg.setStatus(0);
            msg.setResult("快递员录入成功！");
        }else{
            //录入失败
            msg.setStatus(-1);
            msg.setResult("快递员录入失败！");
        }
        String json = JSONUtil.toJSON(msg);
        System.out.println("msg--->JSON="+json);
        return json;
    }

    @ResponseBody("/courier/find.do")
    public String find(HttpServletRequest request,HttpServletResponse response){
        String exPhone = request.getParameter("exPhone");
        Courier courier = CourierService.findByExPhone(exPhone);
        Message msg = new Message();
        if(courier == null){
            msg.setStatus(-1);
            msg.setResult("不存在该手机号注册的快递员信息");
        }else{
            msg.setStatus(0);
            msg.setResult("查询成功");
            msg.setData(courier);
        }
        String json = JSONUtil.toJSON(msg);
        return json;
    }

    @ResponseBody("/courier/update.do")
    public String update(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        String exName = request.getParameter("exName");
        String exPhone = request.getParameter("exPhone");
        String idCard = request.getParameter("idCard");
        String exPassword = request.getParameter("exPassword");
        Courier newCourier = new Courier();
        newCourier.setId(id);
        newCourier.setExName(exName);
        newCourier.setExPhone(exPhone);
        newCourier.setIdCard(idCard);
        newCourier.setExPassword(exPassword);
        System.out.println("id="+id+newCourier);
        boolean flag = CourierService.update(id, newCourier);
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


    @ResponseBody("/courier/delete.do")
    public String delete(HttpServletRequest request,HttpServletResponse response){
        int id = Integer.parseInt(request.getParameter("id"));
        boolean flag = CourierService.delete(id);
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
