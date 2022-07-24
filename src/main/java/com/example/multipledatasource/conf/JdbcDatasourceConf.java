package com.example.multipledatasource.conf;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.example.multipledatasource.base.DatasourceEnum;
import com.example.multipledatasource.base.MultipleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : JJZheng
 * @since 2022/7/19 21:02
 */
@Configuration
public class JdbcDatasourceConf {

    //建立DataSource，将DataSource注入到jdbcTemplate通过类型匹配，将jdbcTemplate通过类型匹配注入到DataSourceTransactionManager

    //从配置文件中读取DataSource信息,注入到bean中
    @Bean(name="dataSourceFirst")
    @ConfigurationProperties(prefix = "spring.datasource.first")
    public DataSource dataSourceFirst(){
        return DruidDataSourceBuilder.create().build();
    }

    //如果有多个就配置多个
    @Bean(name="dataSourceSecond")
    @ConfigurationProperties(prefix = "spring.datasource.second")
    DataSource dataSourceSecond(){
        return DruidDataSourceBuilder.create().build();
    }

    //指定名称注入到Parma中
    @Bean
    JdbcTemplate jdbcTemplate(@Qualifier("dataSourceFirst") DataSource dataSource1){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        /*jdbcTemplate.setDataSource(dataSource1);*/
        jdbcTemplate.setDataSource(this.multiDatasourceChoose(dataSource1,dataSourceSecond()));
        return jdbcTemplate;
    }

//    事务管理
    @Bean
    DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSourceFirst") DataSource dataSource1,
                                                              @Qualifier("dataSourceSecond") DataSource dataSource2){
        //通过多数据源注入afterPropertiesSet
        return new DataSourceTransactionManager(multiDatasourceChoose(dataSource1,dataSource2));
    }

    //传入所有的数据源，根据DataSourceSwitch的数据返回对应的数据源，params的参数如果没有Qualifier，会按照类型注入，会出现多个bean
    @Bean
    //由于有多个DataSource的bean，需要先确定一个优先级
    @Primary
    public DataSource multiDatasourceChoose(@Qualifier("dataSourceFirst") DataSource dataSource1,
                                            @Qualifier("dataSourceSecond") DataSource dataSource2) {
        MultipleDataSource multipleDataSource =new MultipleDataSource();
        //将dataSource1设置为默认的数据源
        multipleDataSource.setDefaultTargetDataSource(dataSource1);
        //将所有的数据源，作为一个map，set进去
        Map<Object, Object> targetDatasource = new HashMap<>();
        targetDatasource.put(DatasourceEnum.FIRST_DATASOURCE.getValue(),dataSource1);
        targetDatasource.put(DatasourceEnum.SECOND_DATASOURCE.getValue(),dataSource2);
        multipleDataSource.setTargetDataSources(targetDatasource);
        return multipleDataSource;
    }
}
