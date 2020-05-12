package com.ismarthealth.notice.component.server.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常量
 */
public interface Constant {
    public static Map<String, Class> childClassMap = new ConcurrentHashMap<>();
    String suffix = "LoadBalancedCalcStrategy";
    int ZK_SESSION_TIMEOUT = 3000;
    int ZK_CONNECTION_TIMEOUT = 10000;
    String ZK_DATA_PATH = "/netty";
    String ZK_SERVER_ADDRESS = "zkServerAddress";
}
