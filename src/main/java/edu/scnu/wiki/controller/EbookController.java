package edu.scnu.wiki.controller;

import edu.scnu.wiki.req.EbookQueryReq;
import edu.scnu.wiki.req.EbookSaveReq;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.EbookQueryResp;
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
    public CommonResp list(EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> commonResp = new CommonResp<>();

        PageResp<EbookQueryResp> ebookResps = ebookService.list(req);
        commonResp.setContent(ebookResps);
        commonResp.setMessage("查询成功");
        return commonResp;
    }

    @GetMapping("/all")
    public CommonResp all(EbookQueryReq req){
        CommonResp<List<EbookQueryResp>> commonResp = new CommonResp<>();

        List<EbookQueryResp> ebookQueryResps = ebookService.all(req);
        commonResp.setContent(ebookQueryResps);
        commonResp.setMessage("查询成功");
        return commonResp;
    }


    @PostMapping("/save")
    public CommonResp all(@RequestBody EbookSaveReq req){
        CommonResp commonResp = new CommonResp();

        int success = ebookService.save(req);
        if (success>0){
            commonResp.setMessage("保存成功");
        }else {
            commonResp.setSuccess(false);
            commonResp.setMessage("保存失败");
        }

        return commonResp;
    }
}
