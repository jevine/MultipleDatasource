package com.example.multipledatasource.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author : JJZheng
 * @since 2022/7/19 22:36
 */

@Mapper
public interface SearchMapper {
    Map<String,Object> getInfoFromSql(String seq);
}
