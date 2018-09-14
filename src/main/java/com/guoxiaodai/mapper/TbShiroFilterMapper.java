package com.guoxiaodai.mapper;

import com.guoxiaodai.pojo.TbShiroFilter;
import com.guoxiaodai.pojo.TbShiroFilterExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbShiroFilterMapper {
    int countByExample(TbShiroFilterExample example);

    int deleteByExample(TbShiroFilterExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbShiroFilter record);

    int insertSelective(TbShiroFilter record);

    List<TbShiroFilter> selectByExample(TbShiroFilterExample example);

    TbShiroFilter selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbShiroFilter record, @Param("example") TbShiroFilterExample example);

    int updateByExample(@Param("record") TbShiroFilter record, @Param("example") TbShiroFilterExample example);

    int updateByPrimaryKeySelective(TbShiroFilter record);

    int updateByPrimaryKey(TbShiroFilter record);
}