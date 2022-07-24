package com.example.multipledatasource.anno;



import com.example.multipledatasource.base.DatasourceEnum;

import java.lang.annotation.*;

/**
 * @author : JJZheng
 * @since 2022/7/19 20:53
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface DatasourceSwitch {
    DatasourceEnum value() default DatasourceEnum.FIRST_DATASOURCE;
}
