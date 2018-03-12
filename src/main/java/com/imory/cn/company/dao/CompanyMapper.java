package com.imory.cn.company.dao;

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
public interface CompanyMapper {

    @Select({
            "select companyName,orderNum,id from org_company where enable = 0",
            "order by orderNum asc limit #{startPos},#{maxRows}"
    })
    List<Map<String, Object>> listOrderCompany(@Param("startPos") int startPos, @Param("maxRows") int maxRows);


    @Select({
            "select id,companyName from org_company where enable = 0",
            "order by orderNum asc limit #{startPos},#{maxRows}"
    })
    List<Map<String, Object>> listCompany(@Param("startPos") int startPos, @Param("maxRows") int maxRows);

    @Select({
            "select id,fileName,fileUrlBak,fileCompanyUrl from excel_file",
            "where state = 1 and companyId = #{companyId}",
            "order by createTime desc limit #{startPos},#{maxRows}"
    })
    List<Map<String, Object>> listCompanyFile(@Param("companyId") Integer companyId, @Param("startPos") int startPos, @Param("maxRows") int maxRows);
}
