package edu.scnu.wiki.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author long
 * @version 1.0
 * @ClassName CategoryQueryReq
 * @description: TODO
 * @date 2023/9/27 22:33
 */
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryQueryReq extends PageReq{
    private Long id;

    private String name;
}
