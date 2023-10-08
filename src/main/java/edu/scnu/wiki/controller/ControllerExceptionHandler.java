package edu.scnu.wiki.controller;

import edu.scnu.wiki.exception.BusinessException;
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
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public CommonResp validExceptionHandler(BusinessException e) {
        CommonResp commonResp = new CommonResp();
        log.warn("业务异常：{}", e.getCode().getDesc());
        commonResp.setSuccess(false);
        commonResp.setMessage(e.getCode().getDesc());
        return commonResp;
    }


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResp validExceptionHandler(Exception e) {
        CommonResp commonResp = new CommonResp();
        log.warn("业务异常 ", e);
        commonResp.setSuccess(false);
        commonResp.setMessage("系统出现异常，请联系管理员");
        return commonResp;
    }


}
