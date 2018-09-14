package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbPanel;
import com.guoxiaodai.pojo.common.ZTreeNode;

import java.util.List;

public interface PanelService {
    List<ZTreeNode> findPanelList(int position,boolean showAll);
    int updatePanel(TbPanel tbPanel);
    int deletePanel(int id);
    int addPanel(TbPanel tbPanel);
}
