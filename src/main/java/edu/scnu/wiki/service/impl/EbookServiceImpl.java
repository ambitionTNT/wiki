package edu.scnu.wiki.service.impl;

import edu.scnu.wiki.domain.Ebook;
import edu.scnu.wiki.mapper.EbookMapper;
import edu.scnu.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Ebook> list() {
        return ebookMapper.selectByExample(null);
    }
}
