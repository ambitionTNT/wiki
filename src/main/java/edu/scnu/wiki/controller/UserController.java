package edu.scnu.wiki.controller;

import com.alibaba.fastjson.JSONObject;
import edu.scnu.wiki.req.UserLoginReq;
import edu.scnu.wiki.req.UserQueryReq;
import edu.scnu.wiki.req.UserSaveReq;
import edu.scnu.wiki.req.UserSaveResetPasswordReq;
import edu.scnu.wiki.resp.*;
import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.service.UserService;
import edu.scnu.wiki.utils.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author long
 * @version 1.0
 * @ClassName UserController
 * @description: TODO
 * @date 2023/10/3 21:47
 */

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;



    @Autowired
    private RedisTemplate redisTemplate;


    @Autowired
    private SnowFlake snowFlake;



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

    @PostMapping("/login")
    public CommonResp login(@Valid @RequestBody UserLoginReq req){
        req.setPassword(DigestUtils.md5DigestAsHex(req.getPassword().getBytes()));

        CommonResp commonResp = new CommonResp();
        UserLoginResp userLoginResp = userService.login(req);


        //生成token ,放入Redis中

        Long token = snowFlake.nextId();
        log.info("生成token:{} ,放入Redis中", token);
        userLoginResp.setToken(token.toString());

        redisTemplate.opsForValue().set(token.toString(), JSONObject.toJSONString(userLoginResp), 3600 * 24 , TimeUnit.SECONDS);
        commonResp.setContent(userLoginResp);

        commonResp.setMessage("用户登录成功");
        return commonResp;
    }

    @GetMapping("/logout/{token}")
    public CommonResp delete(@PathVariable String token){
        CommonResp commonResp = new CommonResp();
        Boolean delete = redisTemplate.delete(token);
        if (delete){
            commonResp.setMessage("删除成功");
        }else {
            commonResp.setMessage("删除失败");
            commonResp.setSuccess(false);
        }
        return commonResp;
    }
}
