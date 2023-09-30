package edu.scnu.wiki.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author long
 * @version 1.0
 * @ClassName CategoryResp
 * @description: TODO
 * @date 2023/9/27 22:32
 */
@Data
public class CategoryQueryResp {
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long id;
    @JsonFormat(shape =JsonFormat.Shape.STRING )
    private Long parent;

    private String name;

    private Integer sort;


}
