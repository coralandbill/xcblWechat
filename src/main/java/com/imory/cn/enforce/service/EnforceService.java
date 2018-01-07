package com.imory.cn.enforce.service;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
public interface EnforceService {

    List<Map<String, Object>> listEnforce(int startPos, int maxRows);

    Map<String, Object> selectById(int id);
}
