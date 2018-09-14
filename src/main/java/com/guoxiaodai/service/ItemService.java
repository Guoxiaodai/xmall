package com.guoxiaodai.service;

import com.guoxiaodai.pojo.TbItem;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.pojo.dto.ItemDto;

public interface ItemService {

    DataTablesResult findItemList(int draw, int start, int length, int cid, String search, String orderColumn, String orderDir);
    DataTablesResult getItemSearchList(int draw, int start, int length, int cid,
                                       String search, String minDate, String maxDate,
                                       String orderCol, String orderDir);

    DataTablesResult getAllItemCount();
    TbItem addItem(ItemDto itemDto);
    TbItem getNormalItemById(Long id);
    TbItem updateItem(Long id,ItemDto itemDto );
    ItemDto findItemById(Long itemId);
    int delItemById(Long itemId);
    TbItem stopItem(Long id,Integer state);

}
