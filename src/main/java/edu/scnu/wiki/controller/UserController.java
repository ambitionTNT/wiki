package edu.scnu.wiki.controller;

import edu.scnu.wiki.req.UserQueryReq;
import edu.scnu.wiki.req.UserSaveReq;
import edu.scnu.wiki.req.UserSaveResetPasswordReq;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.EbookQueryResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.resp.UserQueryResp;
import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName UserController
 * @description: TODO
 * @date 2023/10/3 21:47
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public CommonResp list(@Valid UserQueryReq req){
        CommonResp<PageResp<UserQueryResp>> commonResp = new CommonResp<>();
        PageResp<UserQueryResp> respPageResp = userService.list(req);
        commonResp.setContent(respPageResp);
        commonResp.setSuccess(true);
        commonResp.setMessage("查询成功");
        return commonResp;


    }

    @GetMapping("/all")
    public CommonResp all(UserQueryReq req){
        CommonResp<List<UserQueryResp>> commonResp = new CommonResp<>();
        List<UserQueryResp> queryResps = userService.all(req);
        commonResp.setContent(queryResps);
        commonResp.setMessage("查询成功");
        commonResp.setSuccess(true);
        return commonResp;
    }

    @PostMapping("/save")

    public CommonResp save(@Valid @RequestBody UserSaveReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp();
        int save = userService.save(req);
        if (save > 0){
            commonResp.setMessage("保存成功");
        }else {
            commonResp.setMessage("保存失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }


    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp commonResp = new CommonResp();
        int delete = userService.delete(id);
        if (delete>0){
            commonResp.setMessage("删除成功");
        }else {
            commonResp.setMessage("删除失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }

    @PostMapping("/reset-password")
    public CommonResp resetPassword(@Valid @RequestBody UserSaveResetPasswordReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));
        CommonResp commonResp = new CommonResp();
        int save = userService.resetPassword(req);
        if (save > 0){
            commonResp.setMessage("修改成功");
        }else {
            commonResp.setMessage("修改失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }
}
