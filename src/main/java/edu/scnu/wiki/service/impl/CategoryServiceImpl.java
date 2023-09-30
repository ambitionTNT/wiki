package edu.scnu.wiki.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import edu.scnu.wiki.domain.Category;
import edu.scnu.wiki.domain.CategoryExample;
import edu.scnu.wiki.mapper.CategoryMapper;
import edu.scnu.wiki.mapper.EbookMapper;
import edu.scnu.wiki.req.CategoryQueryReq;
import edu.scnu.wiki.req.CategorySaveReq;
import edu.scnu.wiki.req.EbookSaveReq;
import edu.scnu.wiki.resp.CategoryQueryResp;
import edu.scnu.wiki.resp.EbookQueryResp;
import edu.scnu.wiki.resp.PageResp;
import edu.scnu.wiki.service.CategoryService;
import edu.scnu.wiki.utils.CopyUtil;
import edu.scnu.wiki.utils.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName CategoryServiceImpl
 * @description: TODO
 * @date 2023/9/27 22:38
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SnowFlake snowFlake;
    @Override
    public PageResp<CategoryQueryResp> list(CategoryQueryReq req) {
//        封装查询条件
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        if (!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%" + req.getName() + "%");
        }

//        分页查询条件封装
        PageHelper.startPage(req.getPage(), req.getSize());
        PageResp<CategoryQueryResp> CategoryQueryResp = new PageResp<>();

//        封装查询结果到查询分页中
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);

//        将查询结果转成返回结果类型
        List<CategoryQueryResp> categoryQueryResps = CopyUtil.copyList(categoryList, CategoryQueryResp.class);


//        将结果分装成分页中
        PageResp<CategoryQueryResp> pageResp = new PageResp<>();

        pageResp.setList(categoryQueryResps);
        pageResp.setTotal(pageInfo.getTotal());

        return pageResp;


    }


    @Override
    public List<CategoryQueryResp> all() {

        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");

        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        //        将查询结果转成返回结果类型
        List<CategoryQueryResp> categoryQueryResps = CopyUtil.copyList(categoryList, CategoryQueryResp.class);



        return categoryQueryResps;
    }


    @Override
    public int save(CategorySaveReq req) {

        Category category = CopyUtil.copy(req, Category.class);
        if (ObjectUtils.isEmpty(category.getId())){
            //新增
            category.setId(snowFlake.nextId());
            return categoryMapper.insertSelective(category);
        }else {
            //更改
            return categoryMapper.updateByPrimaryKey(category);
        }
    }

    @Override
    public int delete(long id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }


}
