package com.guoxiaodai.web;

import com.guoxiaodai.pojo.TbBase;
import com.guoxiaodai.pojo.TbOrderItem;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.service.SystemService;
import com.guoxiaodai.utils.IPInfoUtil;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 系统管理Controller
 */
@Controller
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "/sys/base",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbBase> getBase(){
        TbBase base = systemService.getBase();
        return new ResultUtil<TbBase>().setData(base);

    }
    @RequestMapping(value = "/sys/shiro/count",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getShiroCount(){

        int result=systemService.getShiroCount();
        return new ResultUtil<Object>().setData(result);
    }
    @RequestMapping(value = "/sys/shiro/list",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getShiroList(){
        DataTablesResult result=systemService.getShiroList();
        return result;


    }
    @RequestMapping(value = "/sys/weekHot",method = RequestMethod.GET)
    @ResponseBody
    public Result<TbOrderItem> getWeekHot() {

        TbOrderItem tbOrderItem = systemService.getWeekHot();
        return new ResultUtil<TbOrderItem>().setData(tbOrderItem);
    }
    @RequestMapping(value = "/sys/weather",method = RequestMethod.GET)
    @ResponseBody
    public Result<Object> getWeather(HttpServletRequest request) {

        String result = IPInfoUtil.getIpInfo(IPInfoUtil.getIpAddr(request));
        return new ResultUtil<Object>().setData(result);
    }
}
