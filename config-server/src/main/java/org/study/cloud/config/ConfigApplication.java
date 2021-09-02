package org.study.cloud.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 测试成功http://localhost:9050/environment/dev
 * 成功返回：{"name":"environment","profiles":["dev"],"label":null,"version":"ae45791d8c641a1044f003ca352612e1d2d3d5eb","state":null,"propertySources":[]}
 * @author lipo
 * @version v1.0
 * @date 2019-11-05 17:53
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableConfigServer
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }
}
