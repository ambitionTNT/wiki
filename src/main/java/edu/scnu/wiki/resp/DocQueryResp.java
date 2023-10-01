package edu.scnu.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author long
 * @version 1.0
 * @ClassName DocQueryResp
 * @description: TODO
 * @date 2023/10/1 15:18
 */
@Data
public class DocQueryResp {
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long id;
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long ebookId;
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long parent;

    private String name;

    private Integer sort;

    private Integer viewCount;

    private Integer voteCount;

}
