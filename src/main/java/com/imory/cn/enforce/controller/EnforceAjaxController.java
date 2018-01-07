package com.imory.cn.enforce.controller;

import com.imory.cn.enforce.service.EnforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@RestController
@RequestMapping("/wechat/enforce")
public class EnforceAjaxController {

    @Autowired
    private EnforceService enforceService;

    @RequestMapping("/list")
    public Map list(int startPos, int maxRows) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> enforceList = enforceService.listEnforce(startPos, maxRows + 1);
        if (enforceList != null && enforceList.size() > maxRows) {
            enforceList.remove(enforceList.size() - 1);
            map.put("hasMore", true);
        } else {
            map.put("hasMore", false);
        }
        map.put("success", true);
        map.put("enforceList", enforceList);
        return map;
    }
}
