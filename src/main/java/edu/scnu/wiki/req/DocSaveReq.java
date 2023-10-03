package edu.scnu.wiki.req;

import edu.scnu.wiki.domain.Content;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author long
 * @version 1.0
 * @ClassName DocSaveReq
 * @description: TODO
 * @date 2023/10/1 15:17
 */
@Data
public class DocSaveReq {
    private Long id;

    private Long ebookId;

    private Long parent;

    private String name;

    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;


    @NotNull(message = "内容不能为空")
    private String content;

}
