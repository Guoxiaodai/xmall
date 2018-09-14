package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbBaseMapper;
import com.guoxiaodai.mapper.TbOrderItemMapper;
import com.guoxiaodai.mapper.TbShiroFilterMapper;
import com.guoxiaodai.pojo.TbBase;
import com.guoxiaodai.pojo.TbOrderItem;
import com.guoxiaodai.pojo.TbShiroFilter;
import com.guoxiaodai.pojo.TbShiroFilterExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统管理Service
 */
@Service
public class SystemServiceImpl implements SystemService {
    @Autowired
    private TbShiroFilterMapper tbShiroFilterMapper;
    @Autowired
    private TbBaseMapper tbBaseMapper;
    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Value("${BASE_ID}")
    private String BASE_ID;

    @Override
    public List<TbShiroFilter> getShiroFilter() {
        TbShiroFilterExample example=new TbShiroFilterExample();
        example.setOrderByClause("sort_order");
        List<TbShiroFilter> list=tbShiroFilterMapper.selectByExample(example);
        if(list==null){
            throw new XmallException("获取shiro过滤链失败");
        }
        return list;
    }

    @Override
    public TbBase getBase() {
        TbBase tbBase=tbBaseMapper.selectByPrimaryKey(Integer.valueOf(BASE_ID));
        if(tbBase==null){
            throw new XmallException("获取基础设置失败");
        }
        return tbBase;
    }

    @Override
    public int getShiroCount() {
        TbShiroFilterExample example=new TbShiroFilterExample();
        int result = tbShiroFilterMapper.countByExample(example);
        return result;
    }

    @Override
    public DataTablesResult getShiroList() {
        DataTablesResult result=new DataTablesResult();
        TbShiroFilterExample example=new TbShiroFilterExample();
        List<TbShiroFilter> list = tbShiroFilterMapper.selectByExample(example);
        if (list==null){
            throw new XmallException("获得权限失败");
        }
        result.setData(list);
        return result;
    }

    @Override
    public TbOrderItem getWeekHot() {
        List<TbOrderItem> list=tbOrderItemMapper.getWeekHot();
        if(list==null){
            throw new XmallException("获取热销商品数据失败");
        }
        if(list.size()==0){
            TbOrderItem tbOrderItem=new TbOrderItem();
            tbOrderItem.setTotal(0);
            tbOrderItem.setTitle("暂无数据");
            tbOrderItem.setPicPath("");
            return tbOrderItem;
        }
        return list.get(0);
    }
}
