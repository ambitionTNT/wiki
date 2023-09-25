package edu.scnu.wiki.service.impl;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.domain.EbookExample;
import edu.scnu.wiki.mapper.EbookMapper;
import edu.scnu.wiki.req.EbookReq;
import edu.scnu.wiki.resp.EbookResp;
import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.utils.CopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName EbookServiceImpl
 * @description: TODO
 * @date 2023/9/24 16:44
 */
@Service
public class EbookServiceImpl implements EbookService {

    @Autowired
    private EbookMapper ebookMapper;
    @Override
    public List<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);

        return respList;
    }
}
