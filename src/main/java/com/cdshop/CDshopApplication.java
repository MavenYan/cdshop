package com.cdshop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableTransactionManagement // 启动事务管理
@MapperScan(basePackages = {"com.cdshop.modules.*"})
public class CDshopApplication {

    public static void main(String[] args) {
        SpringApplication.run(CDshopApplication.class, args);
    }

}
