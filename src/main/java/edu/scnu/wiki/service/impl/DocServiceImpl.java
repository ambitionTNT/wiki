package edu.scnu.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import edu.scnu.wiki.domain.Content;
import edu.scnu.wiki.domain.Doc;
import edu.scnu.wiki.domain.DocExample;
import edu.scnu.wiki.mapper.ContentMapper;
import edu.scnu.wiki.mapper.DocMapper;
import edu.scnu.wiki.mapper.DocMapperCust;
import edu.scnu.wiki.req.DocQueryReq;
import edu.scnu.wiki.req.DocSaveReq;
import edu.scnu.wiki.resp.DocQueryResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.DocService;
import edu.scnu.wiki.utils.CopyUtil;
import edu.scnu.wiki.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName DocServiceImpl
 * @description: TODO
 * @date 2023/10/1 15:24
 */
@Service
public class DocServiceImpl implements DocService {


    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private DocMapper docMapper;


    @Autowired
    private DocMapperCust docMapperCust;


    @Autowired
    private SnowFlake snowFlake;
    @Override
    public PageResp<DocQueryResp> list(DocQueryReq req) {
        //        封装查询条件
        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }
        //        分页查询条件封装

        PageHelper.startPage(req.getPage(), req.getSize());
        PageResp<DocQueryResp> docQueryRespPageResp  = new PageResp<>();

        //封装查询结果到查询分页中

        List<Doc> docList = docMapper.selectByExample(docExample);
        PageInfo<Doc> pageInfo = new PageInfo<>(docList);

        //        将查询结果转成返回结果类型
        List<DocQueryResp> docQueryResps = CopyUtil.copyList(docList, DocQueryResp.class);

        docQueryRespPageResp.setList(docQueryResps);
        docQueryRespPageResp.setTotal(pageInfo.getTotal());

        return docQueryRespPageResp;



    }

    @Override
    public int save(DocSaveReq req) {
        Doc doc = CopyUtil.copy(req, Doc.class);
        Content content = CopyUtil.copy(req, Content.class);
        int count = 0;
        if (ObjectUtils.isEmpty(doc.getId())){
            //新增
            doc.setId(snowFlake.nextId());
            content.setId(doc.getId());
            count += docMapper.insertSelective(doc);
            count += contentMapper.insertSelective(content);
        }else {
            //更改
            count += docMapper.updateByPrimaryKey(doc);
            count += contentMapper.updateByPrimaryKeyWithBLOBs(content);
            if (count==1){
                count +=contentMapper.insert(content);
            }

        }
        return count;

    }

    @Override
    public int delete(List<String> ids) {

        DocExample docExample = new DocExample();
        DocExample.Criteria criteria = docExample.createCriteria();
        criteria.andIdIn(ids);
        return docMapper.deleteByExample(docExample);
    }

    @Override
    public List<DocQueryResp> all(Long id) {
        DocExample docExample = new DocExample();
        docExample.createCriteria().andEbookIdEqualTo(id);
        docExample.setOrderByClause("sort asc");
        List<Doc> docList = docMapper.selectByExample(docExample);
        List<DocQueryResp> docQueryRespList = CopyUtil.copyList(docList, DocQueryResp.class);

        return docQueryRespList;
    }

    @Override
    public String findContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        int i = docMapperCust.increaseViewCount(id);

        if (ObjectUtils.isEmpty(content)) {
            return "";
        } else {
            return content.getContent();
        }
    }
}
