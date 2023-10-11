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
import java.util.Arrays;
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



    @GetMapping("/all/{ebookId}")
    public CommonResp all(@PathVariable Long ebookId){
        CommonResp<List<DocQueryResp>> commonResp = new CommonResp<>();
        List<DocQueryResp> docQueryResps = docService.all(ebookId);
        if (docQueryResps.size() == 0){
            commonResp.setMessage("查询失败");
            commonResp.setSuccess(false);

        }else {
            commonResp.setMessage("查询成功,暂无这本电子书的文档");
            commonResp.setContent(docQueryResps);
        }

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
        if (save  ==2){
            commonResp.setSuccess(true);
            commonResp.setMessage("插入成功");
        }else {
            commonResp.setMessage("插入失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }


    @DeleteMapping("/delete/{ids}")
    public CommonResp delete(@Valid @PathVariable String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        CommonResp commonResp = new CommonResp();
        int delete = docService.delete(idList);
        if (delete>0){
            commonResp.setSuccess(true);
            commonResp.setMessage("插入成功");
        }else {
            commonResp.setMessage("插入失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }
    @GetMapping("/find/{id}")
    public CommonResp find(@PathVariable Long id){
        CommonResp<String> commonResp = new CommonResp<>();
        String content = docService.findContent(id);
        commonResp.setContent(content);
        return commonResp;
    }

    @GetMapping("/vote/{id}")
    public CommonResp vote(@PathVariable Long id){

        CommonResp commonResp = new CommonResp<>();
        int content = docService.vote(id);
        if (content>0){
            commonResp.setContent(content);
        }else {
            commonResp.setMessage("点赞失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }

}
