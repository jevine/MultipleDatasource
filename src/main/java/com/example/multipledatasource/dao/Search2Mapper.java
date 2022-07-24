package com.example.multipledatasource.dao;

import com.example.multipledatasource.anno.DatasourceSwitch;
import com.example.multipledatasource.base.DatasourceEnum;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * @author : JJZheng
 * @since 2022/7/20 19:27
 */

@DatasourceSwitch(value = DatasourceEnum.SECOND_DATASOURCE)
@Mapper
public interface Search2Mapper {

    Map<String,Object> getInfoFromSql2(String seq);
}
