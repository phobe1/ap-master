package com.you.ap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 应用启动类
 *
 * @author gaoxiaoning created on  17/12/13.
 * @version $Id$
 */
@EnableTransactionManagement
@SpringBootApplication
@PropertySource({
    "classpath:/config/app.properties"
})
@MapperScan("com.you.ap.dao")
@EnableScheduling
public class AppBootstrap {
    public static void main(String[] args) {

        SpringApplication.run(AppBootstrap.class, args);
    }

    }



