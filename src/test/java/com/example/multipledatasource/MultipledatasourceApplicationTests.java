package com.example.multipledatasource;

import com.example.multipledatasource.dao.Search2Mapper;
import com.example.multipledatasource.dao.SearchMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;

@SpringBootTest
class MultipledatasourceApplicationTests {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private Search2Mapper search2Mapper;
    /*@Qualifier("dataSourceSecond")
    DataSource dataSource2;*/
    @Test
    void contextLoads(@Qualifier("dataSourceFirst") DataSource dataSource1) {
        System.out.println(searchMapper.getInfoFromSql("xiaofang"));
        System.out.println(search2Mapper.getInfoFromSql2("3"));
        System.out.println(searchMapper.getInfoFromSql("xiaoming"));
        /*System.out.println(dataSource1.equals(dataSource2));*/
    }

}
