package com.ismarthealth.notice.component.server.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * @Author: LXK
 * @Date 2020年4月22日13:32:27
 * @Description: 获取策略子类
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CalcStrategyConfig {
    public String calcCode();
}
