package com.ismarthealth.notice.component.server.strategy.impl;

import com.ismarthealth.notice.component.server.strategy.LoadBalancedCalcStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @description: 虚拟散列算法
 * @author: Liuxk
 * @create: 2020-04-22 11:24
 **/
@Component
public class HashVirtualLoadBalancedCalcStrategy extends LoadBalancedCalcStrategy {
    @Override
    public String getServer(String client, List<String> list) {
        SortedMap virtualNodes = transIpToVirtul(list);
        int hash = getHash(client);
        // 得到大于该hash值的排好序的map
        SortedMap<Integer, String> subMap = virtualNodes.tailMap(hash);
        // 大于该hash值的第一个元素
        Integer nodeIndex = subMap.firstKey();
        // 如果不存在 大于该hash值的元素 则返回根节点
        if (nodeIndex == null) {
            nodeIndex = (Integer) virtualNodes.firstKey();
        }
        // 返回对应虚拟节点的名称
        return subMap.get(nodeIndex);
    }

    private SortedMap<Integer, String> transIpToVirtul(List<String> list) {
        SortedMap<Integer, String> virtualNodes = new TreeMap<>();
        int VIRTUAL_NODES = 160;
        for (String ip : list) {
            for (int i = 0; i < VIRTUAL_NODES; i++) {
                int hash = getHash(ip + "VN" + i);
                virtualNodes.put(hash, ip);
            }
        }
        return virtualNodes;
    }

    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int) 2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如果算出来的数为负数  取其绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }
}
