package edu.scnu.wiki.controller;

import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.EbookSnapShotResp;
import edu.scnu.wiki.service.EbookSnapShotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookSnapShotController
 * @description: TODO
 * @date 2023/10/13 18:11
 */
@RestController
@Slf4j
@RequestMapping("/ebook-snapshot")
public class EbookSnapShotController {

    @Autowired
    private EbookSnapShotService ebookSnapShotService;

    @RequestMapping("/statistic")
    public CommonResp getStatistic(){
        CommonResp<List<EbookSnapShotResp>> commonResp = new CommonResp<>();
        List<EbookSnapShotResp> statistic = ebookSnapShotService.getStatistic();
        commonResp.setContent(statistic);
        commonResp.setMessage("查询成功");
        return commonResp;
    }
}
