package com.guoxiaodai.web;

import com.guoxiaodai.pojo.TbItemCat;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.pojo.common.ZTreeNode;
import com.guoxiaodai.service.ItemCatService;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品管理Controller
 */
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping(value = "/item/cat/list",method = RequestMethod.GET)
    @ResponseBody
    public List<ZTreeNode> findItemCatList(@RequestParam(name = "id",defaultValue = "0")int parentId){
        List<ZTreeNode> result = itemCatService.findItemCatList(parentId);
        return result;

    }
    @RequestMapping(value = "/item/cat/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> addItemCat(@ModelAttribute TbItemCat tbItemCat){
        itemCatService.addItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/item/cat/update",method = RequestMethod.POST)
    @ResponseBody
    public Result<Object> updateItemCat(@ModelAttribute TbItemCat tbItemCat){
        itemCatService.updateItemCat(tbItemCat);
        return new ResultUtil<Object>().setData(null);

    }
    @RequestMapping(value = "/item/cat/del/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delItemCatById(@PathVariable Long id){
        itemCatService.delItemCatById(id);
        return new ResultUtil<Object>().setData(null);
    }
}
