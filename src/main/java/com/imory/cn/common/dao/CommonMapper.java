package com.imory.cn.common.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
public interface CommonMapper {

    @Select({
            "select * from org_company where logonId = #{logonId} and logonPsd = #{password}"
    })
    Map<String, Object> logon(@Param("logonId") String logonId, @Param("password") String password);

    @Select( "select configId, paramName, paramValue, updateTime from wx_config where paramName=#{paramName}" )
    public Map getWechatParam( @Param( "paramName" )String paramName );
}
