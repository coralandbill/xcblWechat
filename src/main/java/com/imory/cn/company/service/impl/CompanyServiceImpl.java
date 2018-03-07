package com.imory.cn.company.service.impl;

import com.imory.cn.company.dao.CompanyMapper;
import com.imory.cn.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyMapper companyMapper;

    @Override
    public List<Map<String, Object>> listOrderCompany(int startPos, int maxRows) {
        return companyMapper.listOrderCompany(startPos, maxRows);
    }

    @Override
    public List<Map<String, Object>> listCompany(int startPos, int maxRows) {
        return companyMapper.listCompany(startPos, maxRows);
    }

    @Override
    public List<Map<String, Object>> listCompanyFile(Integer companyId, int startPos, int maxRows) {
        return companyMapper.listCompanyFile(companyId, startPos, maxRows);
    }
}
