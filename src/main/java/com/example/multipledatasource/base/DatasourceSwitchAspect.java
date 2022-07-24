package com.example.multipledatasource.base;


import com.example.multipledatasource.anno.DatasourceSwitch;
import com.example.multipledatasource.dao.Search2Mapper;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author : JJ Zheng
 * @since 2022-07-22 11:21
 */
//aspect 不在boot包里面
@Aspect
@Component
//优先级，数值越低，优先级越高
@Order(-1)
@Log4j2
public class DatasourceSwitchAspect {

    /**
     * 切入点为SearchMapper下所有的方法，todo 可以根据需要自行修改
     */
    @Pointcut("execution(* com.example.multipledatasource.dao.SearchMapper.*(..))")
    private void setFirstDatasourcePointCut(){}

    /**
     * 切入点为SearchMapper下所有的方法，todo 可以根据需要自行修改
     */
    @Pointcut("execution(* com.example.multipledatasource.dao.Search2Mapper.*(..))")
    private void setSecondDatasourcePointCut(){}

//该处注释掉的地方，可以根据自己需要，对整个文件夹，进行设置默认数据源，只需要进行微小的修改即可
//    /**
//     *aop 在方法前后进行增强
//     */
//    @Before(value = "setFirstDatasourcePointCut()")
//    private void setFirstDatasource(JoinPoint joinPoint){
//        this.setTargetDatasource(joinPoint,DatasourceEnum.FIRST_DATASOURCE);
//    }
//
//    /**
//     *aop 在方法前后进行增强
//     */
//    @Before(value = "setSecondDatasourcePointCut()")
//    private void setSecondDatasource(JoinPoint joinPoint){
//        this.setTargetDatasource(joinPoint,DatasourceEnum.FIRST_DATASOURCE);
//    }

    /**
     *aop 在方法前后进行增强
     */
    @Before(value = "setSecondDatasourcePointCut()||setFirstDatasourcePointCut()")
    private void setSecondDatasource(JoinPoint joinPoint){
        this.setTargetDatasource(joinPoint);
    }
    /**
     *aop 在方法前后进行增强,将副线程移除
     */
    @After(value = "setSecondDatasourcePointCut()||setFirstDatasourcePointCut()")
    private void removeThreadLocal(){
        ThreadLocalDatasource.removeDatasourceKey();
    }

    private void setTargetDatasource(JoinPoint joinPoint) {

        //获取当前代理的方法，判断当前是否存在注解，如果存在则以方法上的优先级为高
        //这边为啥能强转，还没搞清楚
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DatasourceSwitch annotation=methodSignature.getMethod().getAnnotation(DatasourceSwitch.class);
        if (!Objects.isNull(annotation)){
            String datasourceKey = annotation.value().getValue();
            ThreadLocalDatasource.setDatasourceKey(datasourceKey);
            log.info("切换数据源，当前为{}",DatasourceEnum.valueOfByValueParams(datasourceKey).getDesc());
            return;
        }else {
            //如果当前方法上不存在注解，则获取被代理的对象
            Class currentClass = methodSignature.getDeclaringType();
            System.out.println(currentClass.equals(Search2Mapper.class));
            //获取代理对象上是否存在对应的注解
            /*DatasourceSwitch annotation2 = joinPoint.getTarget().getClass().getAnnotation(DatasourceSwitch.class);*/
            DatasourceSwitch annotationClass = (DatasourceSwitch) currentClass.getAnnotation(DatasourceSwitch.class);
            if (!Objects.isNull(annotationClass)){
                String datasourceKey = annotationClass.value().getValue();
                ThreadLocalDatasource.setDatasourceKey(datasourceKey);
                log.info("切换数据源，当前为{}",DatasourceEnum.valueOfByValueParams(datasourceKey).getDesc());
                return;
            }
        }
        //如果两处都不存在注解，那么走默认的数据源接口，此处默认走数据源一
        ThreadLocalDatasource.setDatasourceKey(DatasourceEnum.FIRST_DATASOURCE.getValue());
        log.info("默认数据源，当前为{}",DatasourceEnum.FIRST_DATASOURCE.getDesc());
    }
}
