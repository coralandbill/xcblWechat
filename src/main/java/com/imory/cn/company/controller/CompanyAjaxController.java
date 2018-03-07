package com.imory.cn.company.controller;

import com.imory.cn.company.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/wechat/companyAjax")
@RestController
public class CompanyAjaxController {
    @Autowired
    private CompanyService companyService;

    @RequestMapping("/listOrder")
    public Map listOrder(int startPos, int maxRows) {
        Map<String, Object> map = new HashMap();
        List<Map<String, Object>> companyList = companyService.listOrderCompany(startPos, maxRows + 1);
        map.put("success", true);
        map.put("companyList", companyList);
        return map;
    }

    @RequestMapping("/list")
    public Map list(int startPos, int maxRows) {
        Map<String, Object> map = new HashMap();
        List<Map<String, Object>> companyList = companyService.listCompany(startPos, maxRows + 1);
        map.put("success", true);
        map.put("companyList", companyList);
        return map;
    }

    @RequestMapping("/fileList")
    public Map fileList(Integer companyId, int startPos, int maxRows) {
        Map<String, Object> map = new HashMap();
        List<Map<String, Object>> fileList = companyService.listCompanyFile(companyId, startPos, maxRows + 1);
        map.put("success", true);
        map.put("fileList", fileList);
        return map;
    }
}
