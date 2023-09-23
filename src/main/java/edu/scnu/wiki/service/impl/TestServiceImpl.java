package edu.scnu.wiki.service.impl;

import edu.scnu.wiki.domain.Test;
import edu.scnu.wiki.mapper.TestMapper;
import edu.scnu.wiki.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName TestServiceImpl
 * @description: TODO
 * @date 2023/9/23 21:32
 */
@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper;
    @Override
    public List<Test> list() {
        return testMapper.selectTest();
    }
}
