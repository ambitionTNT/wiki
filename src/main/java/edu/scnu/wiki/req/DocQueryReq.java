package edu.scnu.wiki.req;

import lombok.Data;

/**
 * @author long
 * @version 1.0
 * @ClassName DocQueryReq
 * @description: TODO
 * @date 2023/10/1 15:17
 */
@Data
public class DocQueryReq extends PageReq{
    private Long id;

    private String name;
}
