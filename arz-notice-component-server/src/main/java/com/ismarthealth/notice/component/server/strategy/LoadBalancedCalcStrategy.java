package com.ismarthealth.notice.component.server.strategy;

import com.ismarthealth.notice.component.server.annotation.CalcStrategyConfig;
import com.ismarthealth.notice.component.server.constant.Constant;
import com.ismarthealth.notice.component.server.strategy.manager.StrategyManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public abstract class LoadBalancedCalcStrategy {
    @Autowired
    private StrategyManager strategyManager;

    public abstract String getServer(String client, List<String> list);

    @PostConstruct
    public void init() {
        String calcCode = getCalcCode();
        Class oldClass = Constant.childClassMap.put(calcCode, getClass());
        if (oldClass != null) {
            throw new RuntimeException("CalcCode!calcCode:" + calcCode);
        }
    }

    private String getCalcCode() {
        if (getClass().isAnnotationPresent(CalcStrategyConfig.class)) {
            CalcStrategyConfig calcStrategyConfigAnnotation = getClass().getAnnotation(CalcStrategyConfig.class);
            String calcCode = calcStrategyConfigAnnotation.calcCode();
            if (StringUtils.isBlank(calcCode)) {
                throw new RuntimeException(getClass().getName() + " CalcCode注解值不能为空");
            }
            return calcCode;
        }
        String className = getClass().getSimpleName();
        String calcCode = className.replace(Constant.suffix, "");
        return strategyManager.toLowerCaseFirstOne(calcCode);
    }

}
