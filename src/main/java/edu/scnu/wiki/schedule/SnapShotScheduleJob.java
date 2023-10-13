package edu.scnu.wiki.schedule;

import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.service.EbookSnapShotService;
import edu.scnu.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author long
 * @version 1.0
 * @ClassName DocScheduleJob
 * @description: TODO
 * @date 2023/10/11 19:44
 */
@Slf4j
@Component
public class SnapShotScheduleJob {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private EbookSnapShotService ebookSnapShotService;
    @Scheduled(cron = "0/30 * *  * * ? ")
    public void cron() throws InterruptedException{

        //增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("生成今日电子书快照开始");
        long start = System.currentTimeMillis();
        ebookSnapShotService.genSnapShot();

        log.info("今日生成电子书快照结束，耗时:{}毫秒", System.currentTimeMillis() - start);

    }
}
