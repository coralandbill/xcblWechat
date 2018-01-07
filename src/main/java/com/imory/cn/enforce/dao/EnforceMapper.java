package com.imory.cn.enforce.dao;

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
public interface EnforceMapper {

    @Select({
            "select ef.*,DATE_FORMAT(enforceDate,'%Y-%m-%d') as enforceDateFormat from enforce ef limit #{startPos},#{maxRows}"
    })
    List<Map<String, Object>> listEnforce(@Param("startPos") int startPos, @Param("maxRows") int maxRows);

    @Select({
            "select *,DATE_FORMAT(enforceDate,'%Y-%m-%d') as enforceDateFormat from enforce where id = #{id}"
    })
    Map<String, Object> selectById(@Param("id") int id);
}
