package com.imory.cn.common.service;

import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
public interface CommonService {

    Map<String, Object> logon(String logonId, String password);

    String getAccess_Token(String appId, String secret, String ACCESS_TOKEN);
}
