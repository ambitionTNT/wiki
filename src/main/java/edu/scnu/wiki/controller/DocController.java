package edu.scnu.wiki.controller;

import edu.scnu.wiki.req.DocQueryReq;
import edu.scnu.wiki.req.DocSaveReq;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.DocQueryResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.DocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName DocController
 * @description: TODO
 * @date 2023/10/1 15:48
 */
@RestController
@RequestMapping("/doc")
public class DocController {
    @Autowired
    private DocService docService;



    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<DocQueryResp>> commonResp = new CommonResp<>();
        List<DocQueryResp> docQueryResps = docService.all();
        commonResp.setMessage("查询成功");
        commonResp.setContent(docQueryResps);
        return commonResp;
    }
    @GetMapping("/list")
    public CommonResp list(@Valid DocQueryReq req){
        PageResp<DocQueryResp> docQueryRespPageResp = docService.list(req);
        CommonResp< PageResp<DocQueryResp>> commonResp = new CommonResp<>();
        commonResp.setContent(docQueryRespPageResp);
        commonResp.setMessage("查询成功");
        return commonResp;
    }
    @PostMapping("/save")

    public CommonResp save(@Valid @RequestBody DocSaveReq docSaveReq){
        CommonResp commonResp = new CommonResp();
        int save = docService.save(docSaveReq);
        if (save > 0){
            commonResp.setSuccess(true);
            commonResp.setMessage("插入成功");
        }else {
            commonResp.setMessage("插入失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }



}
