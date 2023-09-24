package edu.scnu.wiki.controller;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Autowired
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResp list(){
        CommonResp<List<Ebook>> commonResp = new CommonResp<>();
        List<Ebook> ebookList = ebookService.list();
        commonResp.setContent(ebookList);
        commonResp.setMessage("查询成功");
        return commonResp;
    }
}
