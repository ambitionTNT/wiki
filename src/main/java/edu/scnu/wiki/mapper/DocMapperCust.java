package edu.scnu.wiki.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author long
 * @version 1.0
 * @ClassName DocMapperCust
 * @description: TODO
 * @date 2023/10/8 21:35
 */
@Mapper
public interface DocMapperCust {
    int increaseViewCount(@Param("id") Long id);
    int increaseVoteCount(@Param("id") Long id);
    int updateEbookInfo();
}
