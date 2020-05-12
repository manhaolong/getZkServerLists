package com.ismarthealth.notice.component.server.strategy.impl;

import com.ismarthealth.notice.component.server.strategy.LoadBalancedCalcStrategy;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description: 轮训算法
 * @author: Liuxk
 * @create: 2020-04-29 15:59
 **/
@Component
public class RoundRobinLoadBalancedCalcStrategy extends LoadBalancedCalcStrategy {

    private static Integer pos = 0;

    @Override
    public String getServer(String client, List<String> list) {
        String ip = null;
        // 轮训算法
        synchronized (pos) {
            if (pos >= list.size()) {
                pos = 0;
            }
            ip = list.get(pos);
            pos++;
        }
        return ip;
    }
}
