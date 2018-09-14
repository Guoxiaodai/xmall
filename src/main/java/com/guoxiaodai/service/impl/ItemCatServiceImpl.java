package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbItemCatMapper;
import com.guoxiaodai.pojo.TbItemCat;
import com.guoxiaodai.pojo.TbItemCatExample;
import com.guoxiaodai.pojo.common.ZTreeNode;
import com.guoxiaodai.service.ItemCatService;
import com.guoxiaodai.utils.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper tbItemCatMapper;

    @Override
    public List<ZTreeNode> findItemCatList(int parentId) {
        TbItemCatExample example=new TbItemCatExample();
        TbItemCatExample.Criteria criteria=example.createCriteria();
        //排序
        example.setOrderByClause("sort_order");
        //条件查询
        criteria.andParentIdEqualTo(new Long(parentId));
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);

        //转换成ZtreeNode
        List<ZTreeNode> resultList=new ArrayList<>();

        for(TbItemCat tbItemCat:list){

            ZTreeNode node= DtoUtil.TbItemCat2ZTreeNode(tbItemCat);

            resultList.add(node);
        }

        return resultList;
    }

    /**
     * 存在BUG 只能手动添加state和sortorder
     * @param tbItemCat
     * @return
     */
    @Override
    public int addItemCat(TbItemCat tbItemCat) {
        tbItemCat.setCreated(new Date());
        tbItemCat.setUpdated(new Date());
        tbItemCat.setSortOrder(1);
        tbItemCat.setStatus(1);
        if(tbItemCatMapper.insert(tbItemCat)!=1){
            throw new XmallException("添加根节点失败");
        }
        return 1;
    }

    @Override
    public int updateItemCat(TbItemCat tbItemCat) {
        tbItemCat.setUpdated(new Date());
        if ((tbItemCatMapper.updateByPrimaryKey(tbItemCat))!=1){
            throw new XmallException("修改节点失败");
        }
        return 1;
    }

    @Override
    public void delItemCatById(Long id) {
        delZTreeNode(id);
    }

    @Override
    public void delZTreeNode(Long id) {

        //获得该节点下的所有子节点
        List<ZTreeNode> node = findItemCatList(id.intValue());
        if(node.size()>0){
            //如果有子节点，遍历子节点
            for(int i=0;i<node.size();i++){
                delItemCatById((long) node.get(i).getId());
            }
        }
        //没有子节点直接删除
        if(tbItemCatMapper.deleteByPrimaryKey(id)!=1){
            throw new XmallException("删除商品分类失败");
        }
    }

}
