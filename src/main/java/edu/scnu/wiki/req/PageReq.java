package edu.scnu.wiki.req;

import lombok.Data;
import lombok.ToString;

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
    private int page;
    private int size;
}
