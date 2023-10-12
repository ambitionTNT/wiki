package edu.scnu.wiki.config;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ComponentScan("edu.scnu.wiki")
@Slf4j
@MapperScan("edu.scnu.wiki.mapper")
@EnableScheduling
@EnableAsync
public class WikiApplication {
/*
    private static final Logger LOG = LoggerFactory.getLogger(WikiApplication.class);*/

    public static void main(String[] args) {

        SpringApplication application = new SpringApplication(WikiApplication.class);
        ConfigurableEnvironment environment = application.run(args).getEnvironment();
        log.info("启动成功");
        log.info("地址：\thttp://127.0.0.1:{}", environment.getProperty("server.port"));
    }

}
