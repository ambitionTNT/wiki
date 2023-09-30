package edu.scnu.wiki.controller;

import edu.scnu.wiki.req.CategoryQueryReq;
import edu.scnu.wiki.req.CategorySaveReq;
import edu.scnu.wiki.resp.CategoryQueryResp;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName CategoryController
 * @description: TODO
 * @date 2023/9/27 22:29
 */

@RestController
@RequestMapping("/category")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<CategoryQueryResp>> commonResp = new CommonResp<>();
        List<CategoryQueryResp> categoryQueryResps = categoryService.all();
        commonResp.setMessage("查询成功");
        commonResp.setContent(categoryQueryResps);
        return commonResp;
    }

    @GetMapping("/list")
    public CommonResp list(@Valid CategoryQueryReq categoryQueryReq){
        CommonResp<PageResp<CategoryQueryResp>> commonResp = new CommonResp<>();
        PageResp<CategoryQueryResp> pageResp = categoryService.list(categoryQueryReq);
        commonResp.setMessage("查询成功");
        commonResp.setContent(pageResp);
        return commonResp;
    }
    @PostMapping("/save")
    public CommonResp save(@Valid@RequestBody CategorySaveReq categorySaveReq){
        CommonResp commonResp = new CommonResp();
        int save = categoryService.save(categorySaveReq);
        if (save>0){
            commonResp.setSuccess(true);
            commonResp.setMessage("插入成功");
            return commonResp;
        }else {
            commonResp.setSuccess(false);
            commonResp.setMessage("查询失败");
            return commonResp;
        }
    }

    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp commonResp = new CommonResp();
        int delete = categoryService.delete(id);
        if (delete>0){
            commonResp.setMessage("删除成功");
        }else {
            commonResp.setSuccess(false);
            commonResp.setMessage("删除失败");
        }
        return commonResp;

    }






}
