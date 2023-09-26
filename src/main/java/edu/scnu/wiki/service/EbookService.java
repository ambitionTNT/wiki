package edu.scnu.wiki.service;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.req.EbookReq;
import edu.scnu.wiki.resp.CommonResp;
import edu.scnu.wiki.resp.EbookResp;
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
    PageResp<EbookResp>  list(EbookReq req);
    List<EbookResp>  all(EbookReq req);
}
