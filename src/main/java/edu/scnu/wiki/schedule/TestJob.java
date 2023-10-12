package edu.scnu.wiki.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author long
 * @version 1.0
 * @ClassName TestJob
 * @description: TODO
 * @date 2023/10/11 11:38
 */
@Slf4j
//@Component
public class TestJob {
    @Scheduled(fixedDelay = 5000)   //每5秒执行一次
    public void simple() throws InterruptedException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dataFormat = simpleDateFormat.format(new Date());

//        Thread.sleep(2000);
      log.info("每隔5s便会再次执行:{}", dataFormat);
    }

    @Scheduled(cron = "0/2 * *  * * ? ")
    public void cron() throws InterruptedException{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dataFormat = simpleDateFormat.format(new Date());

//        Thread.sleep(2000);
        log.info("每隔2s便会再次执行:{}", dataFormat);
    }
}
