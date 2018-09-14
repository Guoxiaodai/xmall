package com.guoxiaodai.web;

import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单Controller
 */
@Controller
public class OrderController  {
    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/order/count",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getOrderCount(){
        return orderService.getMemberCount();
    }
}
