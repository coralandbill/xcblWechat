package com.imory.cn.news.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
public interface NewsMapper {

    @Select({
            "select nw.*,DATE_FORMAT(newsDate,'%Y-%m-%d') as newsDateFormat from news nw ",
            "where newsType = #{newsType}",
            "order by createTime desc limit #{startPos},#{maxRows}"
    })
    List<Map<String, Object>> listNews(@Param("newsType") int newsType, @Param("startPos") int startPos, @Param("maxRows") int maxRows);

    @Select({
            "select nw.*,DATE_FORMAT(newsDate,'%Y-%m-%d') as newsDateFormat from news nw where id = #{id}"
    })
    Map<String, Object> selectById(@Param("id") Integer id);
}
