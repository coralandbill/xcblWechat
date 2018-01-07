package com.imory.cn.enforce.service.impl;

import com.imory.cn.enforce.dao.EnforceMapper;
import com.imory.cn.enforce.service.EnforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Service
public class EnforceServiceImpl implements EnforceService {

    @Autowired
    private EnforceMapper enforceMapper;

    @Override
    public List<Map<String, Object>> listEnforce(int startPos, int maxRows) {
        return enforceMapper.listEnforce(startPos, maxRows);
    }

    @Override
    public Map<String, Object> selectById(int id) {
        return enforceMapper.selectById(id);
    }
}
