package com.imory.cn.company.service;

import java.util.List;
import java.util.Map;

public interface CompanyService {

    List<Map<String, Object>> listOrderCompany(int startPos, int maxRows);

    List<Map<String, Object>> listCompany(int startPos, int maxRows);

    List<Map<String, Object>> listCompanyFile(Integer companyId, int startPos, int maxRows);
}
