package com.ismarthealth.notice.component.server.strategy.manager;

import com.ismarthealth.notice.component.server.annotation.CalcStrategyConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @description: 策略扩展类
 * @author: Liuxk
 * @create: 2020-04-22 13:36
 **/
@Component
public class StrategyManager {


    public String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }
}
