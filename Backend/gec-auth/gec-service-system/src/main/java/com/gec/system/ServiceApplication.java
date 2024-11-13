package com.gec.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

//@SpringBootApplication(scanBasePackages = {"com.gec.config","com.gec.config2"})
//@SpringBootApplication(scanBasePackages = {"com.gec"})
@SpringBootApplication
@EnableAspectJAutoProxy
@MapperScan("com.gec.system.mapper")
@ComponentScan(basePackages = {"com.gec", "com.gec.util"})
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class,args);
    }
}