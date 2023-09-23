package edu.scnu.wiki.mapper;

import edu.scnu.wiki.domain.Test;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author long
 * @version 1.0
 * @ClassName TestMapper
 * @description: TODO
 * @date 2023/9/23 21:23
 */
@Mapper
public interface TestMapper {
    List<Test> selectTest();

}
