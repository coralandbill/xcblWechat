package com.imory.cn.common.controller;

import com.imory.cn.annotation.SessionCheck;
import com.imory.cn.common.model.OrgAccountInfo;
import com.imory.cn.common.model.OrgCompany;
import com.imory.cn.common.service.CommonService;
import com.imory.cn.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Controller
@RequestMapping("/wechat/common")
public class CommonController {

    @Autowired
    private CommonService commonService;

    @RequestMapping("/login")
    public String login() {
        return null;
    }

    @RequestMapping("/doLogon")
    public String doLogon(String username, String password, HttpSession session, Model model) {
        Map<String, Object> map = commonService.logon(username, MD5Util.MD5(password));
        if (map == null || map.isEmpty()) {
            model.addAttribute("errorMsg", "用户名或密码错误");
            model.addAttribute("username", username);
            return "/wechat/common/login";
        } else {
            OrgCompany orgCompany = new OrgCompany();
            orgCompany.setCompanyName((String) map.get("companyName"));
            orgCompany.setId((Integer) map.get("id"));
            session.setAttribute(OrgCompany.USER_COMPANY, orgCompany);
        }

        String ORIG_URI = (String) session.getAttribute(OrgAccountInfo.ORIG_URI);
        if (StringUtils.isNotBlank(ORIG_URI)) {
            session.setAttribute(OrgAccountInfo.ORIG_URI, "");
            return "redirect:" + ORIG_URI;
        }
        return "redirect:/wechat/news/index.do";
    }
}
