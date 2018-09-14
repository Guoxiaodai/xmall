package com.guoxiaodai.web;

import com.guoxiaodai.pojo.TbPanelContent;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.service.ContentService;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 商城内容管理Controller
 */
@Controller
public class ContentController {
    @Autowired
    private ContentService contentService;
    //导航栏管理
    @RequestMapping(value = "/content/list/{panelId}",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult findPanelContentListByPaneId(@PathVariable int panelId){
        DataTablesResult result = contentService.findPanelContentListByPaneId(panelId);
        return result;
    }
    //添加导航栏内容
    @RequestMapping("/content/add")
    @ResponseBody
    public Result<Object> addContent(@ModelAttribute TbPanelContent tbPanelContent){
        contentService.addPanelContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }
    //删除单个导航栏内容
    @RequestMapping("/content/del/{id}")
    @ResponseBody
    public  Result<Object> delContent(@PathVariable int id){
        contentService.deletePanelContent(id);
        return new ResultUtil<Object>().setData(null);
    }
    //批量删除导航栏内容
    @RequestMapping(value = "/content/del/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delContents(@PathVariable int[] ids){
        for (int id : ids) {
            contentService.deletePanelContent(id);

        }
        return new ResultUtil<Object>().setData(null);
    }
    //修改导航栏内容
    @RequestMapping(value = "/content/update",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> updateContent(@ModelAttribute TbPanelContent tbPanelContent){
        contentService.updatePanelContent(tbPanelContent);
        return new ResultUtil<Object>().setData(null);
    }



}
