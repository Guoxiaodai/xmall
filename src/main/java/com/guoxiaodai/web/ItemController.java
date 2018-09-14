package com.guoxiaodai.web;

import com.guoxiaodai.mapper.TbPanelContentMapper;
import com.guoxiaodai.pojo.TbItem;
import com.guoxiaodai.pojo.TbPanelContent;
import com.guoxiaodai.pojo.TbPanelContentExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.common.Result;
import com.guoxiaodai.pojo.dto.ItemDto;
import com.guoxiaodai.service.ItemService;
import com.guoxiaodai.utils.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品内容Controller
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private TbPanelContentMapper tbPanelContentMapper;

    @RequestMapping(value = "/item/list",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult findItemList(int draw, int start, int length, int cid, @RequestParam("search[value]") String search,
                                         @RequestParam("order[0][column]") int orderCol, @RequestParam("order[0][dir]") String orderDir,
                                         String searchItem, String minDate, String maxDate){

        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        String orderColumn = cols[orderCol];
        if(orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if(orderDir == null) {
            orderDir = "desc";
        }
        DataTablesResult result=itemService.findItemList(draw,start,length,cid,search,orderColumn,orderDir);
        return result;

    }

    @RequestMapping(value = "/item/listSearch",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult getItemSearchList(int draw, int start, int length,int cid,String searchKey,String minDate,String maxDate,
                                              @RequestParam("search[value]") String search, @RequestParam("order[0][column]") int orderCol,
                                              @RequestParam("order[0][dir]") String orderDir){

        //获取客户端需要排序的列
        String[] cols = {"checkbox","id", "image", "title", "sell_point", "price", "created", "updated", "status"};
        //默认排序列
        String orderColumn = cols[orderCol];
        if(orderColumn == null) {
            orderColumn = "created";
        }
        //获取排序方式 默认为desc(asc)
        if(orderDir == null) {
            orderDir = "desc";
        }
        if(!search.isEmpty()){
            searchKey=search;
        }
        DataTablesResult result=itemService.getItemSearchList(draw,start,length,cid,searchKey,minDate,maxDate,orderColumn,orderDir);
        return result;
    }



    @RequestMapping(value = "/item/count",method = RequestMethod.GET)
    @ResponseBody
    public DataTablesResult findItemCount(){
        DataTablesResult result = itemService.getAllItemCount();
        return result;
    }

    @RequestMapping(value = "/item/add",method = RequestMethod.POST)
    @ResponseBody
    public Result<TbItem> addItem(ItemDto itemDto){
        TbItem tbItem = itemService.addItem(itemDto);
        return new ResultUtil<TbItem>().setData(tbItem);
    }
    @RequestMapping(value = "/item/{itemId}",method = RequestMethod.GET)
    @ResponseBody
    public Result<ItemDto> findItemById(@PathVariable Long itemId){
        ItemDto itemDto = itemService.findItemById(itemId);
        return new ResultUtil<ItemDto>().setData(itemDto);

    }


    @RequestMapping(value = "/item/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Result<TbItem> updateItem(@PathVariable Long id,ItemDto itemDto){
        TbItem tbItem = itemService.updateItem(id, itemDto);
        return new ResultUtil<TbItem>().setData(tbItem);

    }
    @RequestMapping(value = "/item/del/{itemIds}",method = RequestMethod.DELETE)
    @ResponseBody
    public Result<Object> delItemById(@PathVariable Long[] itemIds){
        // 判断首页是否关联
        for(Long id:itemIds){
            TbPanelContentExample example=new TbPanelContentExample();
            TbPanelContentExample.Criteria criteria= example.createCriteria();
            criteria.andProductIdEqualTo(id);
            List<TbPanelContent> list =tbPanelContentMapper.selectByExample(example);
            if(list!=null&&list.size()>0){
                return new ResultUtil<Object>().setErrorMsg("删除失败！包含首页展示关联的商品，请先从首页配置中删除");
            }
        }

        for(Long id:itemIds){
            itemService.delItemById(id);
        }
        return new ResultUtil<Object>().setData(null);

    }

    @RequestMapping(value = "/item/stop/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public Result<TbItem> stopItem(@PathVariable Long id){
        TbItem tbItem = itemService.stopItem(id, 0);
        return new ResultUtil<TbItem>().setData(tbItem);

    }
}
