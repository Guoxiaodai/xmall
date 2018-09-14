package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbBase;
import com.guoxiaodai.pojo.TbOrderItem;
import com.guoxiaodai.pojo.TbShiroFilter;
import com.guoxiaodai.pojo.common.DataTablesResult;

import java.util.List;

public interface SystemService {
    List<TbShiroFilter> getShiroFilter();

    TbBase getBase();

    int getShiroCount();

    DataTablesResult getShiroList();

    TbOrderItem getWeekHot();
}
