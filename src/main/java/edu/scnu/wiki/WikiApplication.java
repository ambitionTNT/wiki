package edu.scnu.wiki;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.logging.Logger;

@SpringBootApplication
@Slf4j
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
