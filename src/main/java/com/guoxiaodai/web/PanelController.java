package com.guoxiaodai.web;

import com.guoxiaodai.pojo.TbPanel;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.pojo.common.ZTreeNode;
import com.guoxiaodai.service.PanelService;
import com.guoxiaodai.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *首页板块管理Controller
 */
@Controller
public class PanelController {
    private final static Logger log= LoggerFactory.getLogger(PanelController.class);

    @Autowired
    private PanelService panelService;

    //获得不含轮播的板块类目
    @RequestMapping(value = "/panel/index/list",method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeNode> findPanelList(){
        List<ZTreeNode> list = panelService.findPanelList(0, false);
        return list;
    }
    //获得所有板块类目
    @RequestMapping(value = "/panel/indexAll/list",method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeNode> findPanelAllList(){
        List<ZTreeNode> list = panelService.findPanelList(0, true);
        return list;
    }
    //获得只含轮播图的板块类目
    @RequestMapping(value = "/panel/indexBanner/list",method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeNode> findPanelBannnerList(){
        List<ZTreeNode> list = panelService.findPanelList(-1, true);
        return list;
    }
    //获得其他的板块类目
    @RequestMapping(value = "/panel/other/list",method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeNode> findPanelOtherList(){
        List<ZTreeNode> list=panelService.findPanelList(1,false);
        list.addAll(panelService.findPanelList(2,false));
        return list;
    }
    //修改首页模板
    @RequestMapping(value = "/panel/update",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> updatePanel(@ModelAttribute TbPanel tbPanel){
        panelService.updatePanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }
    //删除首页模板
    @RequestMapping(value = "/panel/del/{ids}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delPanelById(@PathVariable int[] ids){

        for (int id : ids) {
            panelService.deletePanel(id);
        }
        return new ResultUtil<Object>().setData(null);
    }
    //添加首页板块
    @RequestMapping(value = "/panel/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> addPanel(@ModelAttribute TbPanel tbPanel){
        panelService.addPanel(tbPanel);
        return new ResultUtil<Object>().setData(null);
    }
}
