package com.example.multipledatasource.base;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : JJZheng
 * @since 2022/7/20 20:33
 */
public class MultipleDataSource extends AbstractRoutingDataSource {

    /**
     * 重新父类方法，通过该方法，决定最终使用哪个数据源
     * @return 返回最后lookup的DataSource的key
     */
    @Override
    protected String determineCurrentLookupKey() {
        return ThreadLocalDatasource.getDatasourceKey();
    }
}
