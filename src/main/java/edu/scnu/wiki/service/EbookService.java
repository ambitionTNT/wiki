package edu.scnu.wiki.service;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.resp.CommonResp;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookService
 * @description: TODO
 * @date 2023/9/24 16:43
 */
public interface EbookService {
    List<Ebook>list();
}
