package edu.scnu.wiki.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookReq
 * @description: TODO
 * @date 2023/9/24 20:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EbookReq {
    private Long id;

    private String name;

}