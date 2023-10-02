package edu.scnu.wiki.service;

import edu.scnu.wiki.req.CategoryQueryReq;
import edu.scnu.wiki.req.CategorySaveReq;
import edu.scnu.wiki.req.DocQueryReq;
import edu.scnu.wiki.req.DocSaveReq;
import edu.scnu.wiki.resp.CategoryQueryResp;
import edu.scnu.wiki.resp.DocQueryResp;
import edu.scnu.wiki.resp.PageResp;

import java.util.List;

/**
 * @ClassName DocService
 * @description: TODO
 * @author long
 * @date 2023/10/1 15:14
 * @version 1.0
 */
public interface DocService {
    PageResp<DocQueryResp> list(DocQueryReq req);

    int save(DocSaveReq req);

    public int delete(List<String> ids);

    List<DocQueryResp> all();
}
