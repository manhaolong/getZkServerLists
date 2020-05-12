package com.ismarthealth.notice.component.server;

import com.ismarthealth.notice.component.server.discovery.ServiceDiscovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @description: netty长连接信息获取服务启动类
 * @author: Liuxk
 * @create: 2020-04-22 10:45
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class Application implements CommandLineRunner {

    @Autowired
    private ServiceDiscovery serviceDiscovery;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        serviceDiscovery.initZk();
    }
}
