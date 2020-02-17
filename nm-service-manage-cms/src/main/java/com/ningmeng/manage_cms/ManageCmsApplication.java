package com.ningmeng.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Lenovo on 2020/2/11.
 */
@SpringBootApplication
@EntityScan("com.ningmeng.framework.domain.cms")//扫描实体类
@ComponentScan(basePackages={"com.ningmeng.api"})//扫描接口
@ComponentScan(basePackages={"com.ningmeng.manage_cms"})//扫描本项目下所有类
@ComponentScan(basePackages={"com.ningmeng.framework"})//扫描本项目下所有类
public class ManageCmsApplication {
    public static void  main(String[] args){
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
