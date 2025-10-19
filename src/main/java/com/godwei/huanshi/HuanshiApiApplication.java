package com.godwei.huanshi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.godwei.huanshi.mapper")
public class HuanshiApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HuanshiApiApplication.class, args);
    }

}
