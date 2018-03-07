package com.imory.cn.company.controller;

import com.imory.cn.annotation.SessionCheck;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/wechat/company")
@Controller
public class CompanyController {
    @RequestMapping("/index")
    @SessionCheck
    public String index(Model model) {
        return null;
    }

    @RequestMapping("/list")
    @SessionCheck
    public String list(Model model) {
        return null;
    }

    @RequestMapping("/detail")
    @SessionCheck
    public String detail(Integer id, Model model) {
        model.addAttribute("companyId", id);
        return null;
    }
}
