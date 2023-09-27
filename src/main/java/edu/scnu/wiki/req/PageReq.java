package edu.scnu.wiki.req;

import lombok.Data;

import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @author long
 * @version 1.0
 * @ClassName PageReq
 * @description: TODO
 * @date 2023/9/26 21:17
 */
@Data
@ToString
public class PageReq {

    @NotNull(message = "页号不能为空")
    private int page;

    @NotNull(message = "每页条数不能为空")
    @Max(value = 1000, message = "每页条数不能超过1000")
    private int size;
}
