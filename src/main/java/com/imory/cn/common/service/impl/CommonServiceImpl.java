package com.imory.cn.common.service.impl;

import com.imory.cn.common.dao.CommonMapper;
import com.imory.cn.common.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public Map<String, Object> logon(String logonId, String password) {
        return commonMapper.logon(logonId, password);
    }
}
