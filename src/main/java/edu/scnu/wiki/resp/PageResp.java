package edu.scnu.wiki.resp;

import lombok.Data;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName PageResp
 * @description: TODO
 * @date 2023/9/26 21:25
 */
@Data
public class PageResp <T> {
    private long total;
    private List<T> list;
}
