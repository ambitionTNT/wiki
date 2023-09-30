package edu.scnu.wiki.service;

import edu.scnu.wiki.req.CategoryQueryReq;
import edu.scnu.wiki.req.CategorySaveReq;
import edu.scnu.wiki.req.EbookQueryReq;
import edu.scnu.wiki.req.EbookSaveReq;
import edu.scnu.wiki.resp.CategoryQueryResp;

import edu.scnu.wiki.resp.EbookQueryResp;
import edu.scnu.wiki.resp.PageResp;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName CategoryService
 * @description: TODO
 * @date 2023/9/27 22:31
 */
public interface CategoryService {
    PageResp<CategoryQueryResp> list(CategoryQueryReq req);

    int save(CategorySaveReq req);

    int delete(long id);

    List<CategoryQueryResp> all();

}
