package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbItemMapper;
import com.guoxiaodai.mapper.TbPanelContentMapper;
import com.guoxiaodai.pojo.TbItem;
import com.guoxiaodai.pojo.TbPanelContent;
import com.guoxiaodai.pojo.TbPanelContentExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.service.ContentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商城内容管理Service
 */
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private TbPanelContentMapper tbPanelContentMapper;
    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public DataTablesResult findPanelContentListByPaneId(int panelId) {
        DataTablesResult dataTablesResult = new DataTablesResult();
        TbPanelContentExample example =new TbPanelContentExample();
        TbPanelContentExample.Criteria criteria = example.createCriteria();
        criteria.andPanelIdEqualTo(panelId);
        List<TbPanelContent> list = tbPanelContentMapper.selectByExample(example);
        for (TbPanelContent tbPanelContent : list) {
            if (tbPanelContent.getProductId() != null) {
                TbItem tbItem =tbItemMapper.selectByPrimaryKey(tbPanelContent.getProductId());
                tbPanelContent.setProductName(tbItem.getTitle());
                tbPanelContent.setSalePrice(tbItem.getPrice());
                tbPanelContent.setSubTitle(tbItem.getSellPoint());
            }
        }
        dataTablesResult.setData(list);
        return dataTablesResult;
    }

    @Override
    public int addPanelContent(TbPanelContent tbPanelContent) {
        tbPanelContent.setCreated(new Date());
        tbPanelContent.setUpdated(new Date());
        if(tbPanelContentMapper.insert(tbPanelContent)!=1){
            throw new XmallException("添加导航栏内容失败");
        }
        return 1;
    }

    @Override
    public int deletePanelContent(int id) {
        if(tbPanelContentMapper.deleteByPrimaryKey(id)!=1){
            throw new XmallException("删除导航栏内容失败");
        }
        return 1;
    }

    @Override
    public int updatePanelContent(TbPanelContent tbPanelContent) {
        TbPanelContent old=getTbPanelContentById(tbPanelContent.getId());
        if(StringUtils.isBlank(tbPanelContent.getPicUrl())){
            tbPanelContent.setPicUrl(old.getPicUrl());
        }
        if(StringUtils.isBlank(tbPanelContent.getPicUrl2())){
            tbPanelContent.setPicUrl2(old.getPicUrl2());
        }
        if(StringUtils.isBlank(tbPanelContent.getPicUrl3())){
            tbPanelContent.setPicUrl3(old.getPicUrl3());
        }
        tbPanelContent.setCreated(old.getCreated());
        tbPanelContent.setUpdated(new Date());
        if(tbPanelContentMapper.updateByPrimaryKey(tbPanelContent)!=1){
            throw new XmallException("更新板块内容失败");
        }
        return 1;
    }

    @Override
    public TbPanelContent getTbPanelContentById(int id) {
        TbPanelContent tbPanelContent = tbPanelContentMapper.selectByPrimaryKey(id);
        if(tbPanelContent==null){
            throw new XmallException("通过id获取板块内容失败");
        }
        return tbPanelContent;
    }
}
