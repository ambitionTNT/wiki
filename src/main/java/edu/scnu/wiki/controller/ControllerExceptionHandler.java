package edu.scnu.wiki.controller;

import edu.scnu.wiki.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindException;


/**
 * @author long
 * @version 1.0
 * @ClassName ControllerExceptionHandler
 * @description: TODO
 * @date 2023/9/27 19:12
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {


    @ExceptionHandler(BindException.class)
    @ResponseBody
    public CommonResp validExceptionHandler(org.springframework.validation.BindException e) {
        CommonResp commonResp = new CommonResp();
        log.warn("参数校验失败：{}", e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return commonResp;
    }

}
