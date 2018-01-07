package com.imory.cn.enforce.controller;

import com.imory.cn.enforce.service.EnforceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Controller
@RequestMapping("/wechat/enforce")
public class EnforceController {

    @Autowired
    private EnforceService enforceService;

    @RequestMapping("/index")
    public String index() {
        return null;
    }

    @RequestMapping("/detail")
    public String detail(Integer id, Model model) {
        Map<String, Object> enforceDetail = enforceService.selectById(id);
        model.addAttribute("enforceDetail", enforceDetail);
        return null;
    }

}
