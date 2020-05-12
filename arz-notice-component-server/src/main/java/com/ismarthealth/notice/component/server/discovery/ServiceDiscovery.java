package com.ismarthealth.notice.component.server.discovery;


import com.ismarthealth.notice.component.server.constant.Constant;
import com.ismarthealth.notice.component.server.exception.BaseException;
import com.ismarthealth.notice.component.server.serializer.MyZkSerializer;
import com.ismarthealth.notice.component.server.strategy.LoadBalancedCalcStrategy;
import com.ismarthealth.notice.component.server.utils.SpringUtils;
import com.ismarthealth.osp.core.common.enums.ReturnCodeEnum;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @description: zookeeper注册信息获取
 * @author: Liuxk
 * @create: 2020年4月28日16:36:22
 **/
@Component
public class ServiceDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ServiceDiscovery.class);
    private static final String NETTY_PATH = Constant.ZK_DATA_PATH;
    private static final Set<String> addressCache = new HashSet<>();
    private ZkClient zk;
    @Autowired
    private Environment environment;

    public void initZk() throws IOException {
        zk = connectServer();
    }

    /**
     * 通过服务发现，获取服务提供方的地址
     *
     * @return
     */
    public String discover(String key, String calcCode) {
        String connectInfo = null;
        // 声明一个集合用来转换zk上获取的节点信息
        List<String> connectInfoList = new ArrayList<>();
        Set<String> serverList = getServerList();
        if (!CollectionUtils.isEmpty(serverList)) {
            // 使用策略模式 统一管理负载均衡算法
            LoadBalancedCalcStrategy strategy = SpringUtils.getBean(calcCode + Constant.suffix, LoadBalancedCalcStrategy.class);
            if (Objects.isNull(strategy)) {
                throw new BaseException(ReturnCodeEnum.CODE_2126002.getCode(), "未匹配到对应算法策略");
            }
            // 往节点信息集合中打入连接信息数据
            serverList.forEach(node -> {
                connectInfoList.add(zk.readData(NETTY_PATH + "/" + node));
            });
            connectInfo = strategy.getServer(key, connectInfoList);
            if (logger.isInfoEnabled()) {
                logger.info("登陆人员id :{},获取到对应的连接信息为:{}", key, connectInfo);
            }
        }
        return connectInfo;
    }

    /**
     * 连接 zookeeper
     *
     * @return
     */
    private ZkClient connectServer() throws IOException {
        zk = new ZkClient(environment.getProperty(Constant.ZK_SERVER_ADDRESS), Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT, new MyZkSerializer());
        //监听NETTY_PATH下的子文件是否发生变化
        zk.subscribeChildChanges(NETTY_PATH, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                logger.info("进入节点变化监听事件....");
                addressCache.clear();
                addressCache.addAll(currentChilds);
            }
        });
        return zk;
    }

    /**
     * zookeeper上发现服务并加入到缓存中
     *
     * @return
     */
    public Set<String> getServerList() {
        logger.info("当前缓存:{}", addressCache);
        if (!CollectionUtils.isEmpty(addressCache)) {
            return addressCache;
        }
        List<String> addressList = zk.getChildren(NETTY_PATH);
        if (CollectionUtils.isEmpty(addressList)) {
            throw new BaseException(ReturnCodeEnum.CODE_2126001.getCode(), NETTY_PATH + "对应节点上未找到连接信息");
        }
        addressCache.clear();
        addressCache.addAll(addressList);
        return addressCache;
    }
}