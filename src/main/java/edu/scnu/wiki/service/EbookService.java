package edu.scnu.wiki.service;

import edu.scnu.wiki.req.EbookQueryReq;
import edu.scnu.wiki.req.EbookSaveReq;
import edu.scnu.wiki.resp.EbookQueryResp;
import edu.scnu.wiki.resp.PageResp;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookService
 * @description: TODO
 * @date 2023/9/24 16:43
 */
public interface EbookService {
    PageResp<EbookQueryResp>  list(EbookQueryReq req);
    List<EbookQueryResp>  all(EbookQueryReq req);

    int save(EbookSaveReq req);

    int delete(long id);
}
