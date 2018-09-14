package com.guoxiaodai.service.impl;

import com.guoxiaodai.common.exception.XmallException;
import com.guoxiaodai.mapper.TbOrderMapper;
import com.guoxiaodai.pojo.TbOrderExample;
import com.guoxiaodai.pojo.common.DataTablesResult;
import com.guoxiaodai.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private TbOrderMapper tbOrderMapper;
    @Override
    public DataTablesResult getMemberCount() {
        DataTablesResult result=new DataTablesResult();
        TbOrderExample example=new TbOrderExample();
        try{

            result.setRecordsTotal((int) tbOrderMapper.countByExample(example));
        }catch (Exception e){
            throw new XmallException("统计会员数失败");
        }

        return result;
    }
}
