package edu.scnu.wiki.mapper;

import edu.scnu.wiki.resp.EbookSnapShotResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @version 1.0
 * @Description: 电子书快照
 * @Author: long
 * @Date: 2023/10/13 15:33
 */

@Mapper
public interface EbookSnapShotMapperCust {
    int updateEbookSnapShot();
    List<EbookSnapShotResp> selectStatistic();
}
