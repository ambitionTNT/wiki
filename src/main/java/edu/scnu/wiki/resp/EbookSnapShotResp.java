package edu.scnu.wiki.resp;

import lombok.Data;

import java.util.Date;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookSnapShotResp
 * @description: TODO
 * @date 2023/10/13 18:13
 */
@Data

public class EbookSnapShotResp {
    private Date date;

    private Integer viewCount;

    private Integer voteCount;

    private Integer viewIncrease;

    private Integer voteIncrease;

}
