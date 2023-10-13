package edu.scnu.wiki.service;

import edu.scnu.wiki.resp.EbookSnapShotResp;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookSnapShotService
 * @description: TODO
 * @date 2023/10/13 15:39
 */
public interface EbookSnapShotService {
    int genSnapShot();
    List<EbookSnapShotResp> getStatistic();
}
