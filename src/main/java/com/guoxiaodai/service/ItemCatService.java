package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbItemCat;
import com.guoxiaodai.pojo.common.ZTreeNode;

import java.util.List;

public interface ItemCatService {
    List<ZTreeNode> findItemCatList(int parentId);
    int addItemCat(TbItemCat tbItemCat);
    int updateItemCat(TbItemCat tbItemCat);
    void delItemCatById(Long id);
    void delZTreeNode(Long id);

}
