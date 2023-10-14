package edu.scnu.wiki.service.impl;

import edu.scnu.wiki.mapper.EbookSnapShotMapperCust;
import edu.scnu.wiki.resp.EbookSnapShotResp;
import edu.scnu.wiki.service.EbookSnapShotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookSnapShotServiceImpl
 * @description: TODO
 * @date 2023/10/13 15:40
 */

@Service
public class EbookSnapShotServiceImpl implements EbookSnapShotService {

    @Autowired
    private EbookSnapShotMapperCust ebookSnapShotMapperCust;
    @Override
    public int genSnapShot() {
        return ebookSnapShotMapperCust.updateEbookSnapShot();
    }

    @Override
    public List<EbookSnapShotResp> getStatistic() {
        return ebookSnapShotMapperCust.selectStatistic();
    }

    @Override
    public List<EbookSnapShotResp> get30Statistic() {
        return ebookSnapShotMapperCust.select30Statistic();
    }
}
