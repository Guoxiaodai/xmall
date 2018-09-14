package com.guoxiaodai.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbItemCatMapper;
import com.guoxiaodai.mapper.TbItemDescMapper;
import com.guoxiaodai.mapper.TbItemMapper;
import com.guoxiaodai.pojo.TbItem;
import com.guoxiaodai.pojo.TbItemCat;
import com.guoxiaodai.pojo.TbItemDesc;
import com.guoxiaodai.pojo.TbItemExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.dto.ItemDto;
import com.guoxiaodai.service.ItemService;
import com.guoxiaodai.utils.DtoUtil;
import com.guoxiaodai.utils.IDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品内容Service
 */
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public DataTablesResult findItemList(int draw, int start, int length, int cid, String search,
                                         String orderCol, String orderDir) {

        DataTablesResult result=new DataTablesResult();

        //分页执行查询返回结果
        PageHelper.startPage(start/length+1,length);
        List<TbItem> list = tbItemMapper.selectItemByCondition(cid,"%"+search+"%",orderCol,orderDir);
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        result.setRecordsFiltered((int)pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());

        result.setDraw(draw);
        result.setData(list);

        return result;
        }


        
    @Override
    public DataTablesResult getItemSearchList(int draw, int start, int length,int cid, String search,
                                              String minDate, String maxDate, String orderCol, String orderDir) {

        DataTablesResult result=new DataTablesResult();

        //分页执行查询返回结果
        PageHelper.startPage(start/length+1,length);
        List<TbItem> list = tbItemMapper.selectItemByMultiCondition(cid,"%"+search+"%",minDate,maxDate,orderCol,orderDir);
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        result.setRecordsFiltered((int)pageInfo.getTotal());
        result.setRecordsTotal(getAllItemCount().getRecordsTotal());

        result.setDraw(draw);
        result.setData(list);

        return result;
    }

    @Override
    public DataTablesResult getAllItemCount() {
        TbItemExample example=new TbItemExample();
        Long count=tbItemMapper.countByExample(example);
        DataTablesResult result=new DataTablesResult();
        result.setRecordsTotal(Math.toIntExact(count));
        return result;
    }

    @Override
    public TbItem addItem(ItemDto itemDto) {
        long id= IDUtil.getRandomId();
        TbItem tbItem= DtoUtil.ItemDto2TbItem(itemDto);
        tbItem.setId(id);
        tbItem.setStatus((byte) 1);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        if(tbItem.getImage().isEmpty()){
            tbItem.setImage("");
        }
        if(tbItemMapper.insert(tbItem)!=1){
            throw new XmallException("添加商品失败");
        }

        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());

        if(tbItemDescMapper.insert(tbItemDesc)!=1){
            throw new XmallException("添加商品详情失败");
        }

        return getNormalItemById(id);
    }

    @Override
    public TbItem getNormalItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public TbItem updateItem(Long id, ItemDto itemDto) {

        TbItem oldTbItem=getNormalItemById(id);

        TbItem tbItem= DtoUtil.ItemDto2TbItem(itemDto);

        if(tbItem.getImage().isEmpty()){
            tbItem.setImage(oldTbItem.getImage());
        }
        tbItem.setId(id);
        tbItem.setStatus(oldTbItem.getStatus());
        tbItem.setCreated(oldTbItem.getCreated());
        tbItem.setUpdated(new Date());
        if(tbItemMapper.updateByPrimaryKey(tbItem)!=1){
            throw new XmallException("更新商品失败");
        }

        TbItemDesc tbItemDesc=new TbItemDesc();

        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(itemDto.getDetail());
        tbItemDesc.setUpdated(new Date());
        tbItemDesc.setCreated(oldTbItem.getCreated());

        if(tbItemDescMapper.updateByPrimaryKey(tbItemDesc)!=1){
            throw new XmallException("更新商品详情失败");
        }

        return getNormalItemById(id);
    }

    @Override
    public ItemDto findItemById(Long itemId) {
        ItemDto itemDto=new ItemDto();

        TbItem tbItem=tbItemMapper.selectByPrimaryKey(itemId);
        itemDto= DtoUtil.TbItem2ItemDto(tbItem);

        TbItemCat tbItemCat=tbItemCatMapper.selectByPrimaryKey(itemDto.getCid());
        itemDto.setCname(tbItemCat.getName());

        TbItemDesc tbItemDesc=tbItemDescMapper.selectByPrimaryKey(itemId);
        itemDto.setDetail(tbItemDesc.getItemDesc());

        return itemDto;
    }

    @Override
    public int delItemById(Long itemId) {
        if (tbItemMapper.deleteByPrimaryKey(itemId)!=1){
            throw  new XmallException("删除商品失败");
        }
        return 1;
    }

    @Override
    public TbItem stopItem(Long id,Integer state) {
        TbItem tbMember = getNormalItemById(id);
        tbMember.setStatus(state.byteValue());
        tbMember.setUpdated(new Date());

        if (tbItemMapper.updateByPrimaryKey(tbMember) != 1){
            throw new XmallException("修改商品状态失败");
        }
        return getNormalItemById(id);
    }

}
