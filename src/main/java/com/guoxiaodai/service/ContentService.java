package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbPanelContent;
import com.guoxiaodai.pojo.common.DataTablesResult;

public interface ContentService {
    DataTablesResult findPanelContentListByPaneId(int panelId);
    int addPanelContent(TbPanelContent tbPanelContent);
    int deletePanelContent(int id);
    int updatePanelContent(TbPanelContent tbPanelContent);
    TbPanelContent getTbPanelContentById(int id);

}
