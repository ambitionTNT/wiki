package edu.scnu.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.domain.EbookExample;
import edu.scnu.wiki.mapper.EbookMapper;
import edu.scnu.wiki.req.EbookReq;
import edu.scnu.wiki.resp.EbookResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.EbookService;
import edu.scnu.wiki.utils.CopyUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class EbookServiceImpl implements EbookService {

    @Autowired
    private EbookMapper ebookMapper;
    @Override
    public PageResp<EbookResp> list(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        PageHelper.startPage(req.getPage(),req.getSize());
        PageResp<EbookResp> pageResp = new PageResp<>();

        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);

        PageInfo<Ebook> pageInfo = new PageInfo<>(ebookList);



        log.info("总行数：",pageInfo.getTotal());
        log.info("总行数：",pageInfo.getPages());
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(respList);
        return pageResp;
    }

    @Override
    public List<EbookResp> all(EbookReq req) {
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        List<Ebook> ebookList = ebookMapper.selectByExample(ebookExample);
        List<EbookResp> respList = CopyUtil.copyList(ebookList, EbookResp.class);
        return respList;
    }
}
