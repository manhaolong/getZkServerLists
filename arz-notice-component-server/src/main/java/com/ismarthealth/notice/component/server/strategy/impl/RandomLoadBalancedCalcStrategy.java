package com.ismarthealth.notice.component.server.strategy.impl;

import com.ismarthealth.notice.component.server.strategy.LoadBalancedCalcStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 随机算法获取
 * @author: Liuxk
 * @create: 2020-04-22 15:08
 **/
@Component
public class RandomLoadBalancedCalcStrategy extends LoadBalancedCalcStrategy {
    @Override
    public String getServer(String client, List<String> list) {
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(list.size());
        return list.get(randomPos);
    }
}
