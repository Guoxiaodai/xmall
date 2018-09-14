package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbPanelMapper;
import com.guoxiaodai.pojo.TbPanel;
import com.guoxiaodai.pojo.TbPanelExample;
import com.guoxiaodai.pojo.common.ZTreeNode;
import com.guoxiaodai.service.PanelService;
import com.guoxiaodai.utils.DtoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 首页板块管理Service
 */

@Service
public class PanelServiceImpl implements PanelService {
    @Autowired
    private TbPanelMapper tbPanelMapper;


    public  TbPanel getTbpanelById(int id){
        TbPanel tbPanel = tbPanelMapper.selectByPrimaryKey(id);
        if(tbPanel==null){
            throw new XmallException("通过id获取板块内容失败");
        }
        return tbPanel;
    }
    @Override
    public List<ZTreeNode> findPanelList(int position, boolean showAll) {
        TbPanelExample example=new TbPanelExample();
        TbPanelExample.Criteria criteria=example.createCriteria();
        if(position==0&&!showAll){
            //除去非轮播
            criteria.andTypeNotEqualTo(0);
        }else if(position==-1){
            //仅含轮播
            position=0;
            criteria.andTypeEqualTo(0);
        }
        //首页板块
        criteria.andPositionEqualTo(position);
        example.setOrderByClause("sort_order");
        List<TbPanel> panelList=tbPanelMapper.selectByExample(example);

        List<ZTreeNode> list=new ArrayList<>();

        for(TbPanel tbPanel:panelList){
            ZTreeNode zTreeNode= DtoUtil.TbPanel2ZTreeNode(tbPanel);
            list.add(zTreeNode);
        }

        return list;
    }

    @Override
    public int updatePanel(TbPanel tbPanel) {
        TbPanel old = getTbpanelById(tbPanel.getId());
        tbPanel.setUpdated(new Date());
        if (tbPanelMapper.updateByPrimaryKey(tbPanel)!=1){
            throw new XmallException("更新板块失败");
        }
        return  1;
    }

    @Override
    public int deletePanel(int id) {
        if (tbPanelMapper.deleteByPrimaryKey(id)!=1){
            throw new XmallException("删除板块失败");
        }
        return 1;


    }

    @Override
    public int addPanel(TbPanel tbPanel) {
        tbPanel.setCreated(new Date());
        tbPanel.setUpdated(new Date());
        if(tbPanelMapper.insert(tbPanel)!=1){
            throw new XmallException("添加板块失败");
        }
        return 1;
    }
}
