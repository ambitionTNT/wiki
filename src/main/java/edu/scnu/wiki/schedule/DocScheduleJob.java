package edu.scnu.wiki.schedule;

import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author long
 * @version 1.0
 * @ClassName DocScheduleJob
 * @description: TODO
 * @date 2023/10/11 19:44
 */
@Slf4j
@Component
public class DocScheduleJob {

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private EbookService ebookService;
    @Scheduled(cron = "0/30 * *  * * ? ")
    public void cron() throws InterruptedException{

        //增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        log.info("更新电子书下的文档数据开始");
        long start = System.currentTimeMillis();
        ebookService.updateEbook();

        log.info("每隔30s便会再次执行:{}", System.currentTimeMillis() - start);

    }
}
