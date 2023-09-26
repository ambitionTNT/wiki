package edu.scnu.wiki.controller;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.req.EbookReq;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.EbookResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookController
 * @description: TODO
 * @date 2023/9/24 17:00
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<PageResp<EbookResp>> commonResp = new CommonResp<>();

        PageResp<EbookResp> ebookResps = ebookService.list(req);
        commonResp.setContent(ebookResps);
        commonResp.setMessage("查询成功");
        return commonResp;
    }

    @GetMapping("/all")
    public CommonResp all(EbookReq req){
        CommonResp<List<EbookResp>> commonResp = new CommonResp<>();

        List<EbookResp> ebookResps = ebookService.all(req);
        commonResp.setContent(ebookResps);
        commonResp.setMessage("查询成功");
        return commonResp;
    }
}
