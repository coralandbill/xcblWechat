package com.imory.cn.inform.controller;

import com.imory.cn.common.model.OrgAccountInfo;
import com.imory.cn.common.model.OrgCompany;
import com.imory.cn.inform.dao.UserInformMapper;
import com.imory.cn.inform.dto.UserInform;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/14
 */
@RestController
@RequestMapping("/wechat/informAjax")
public class InformAjaxController {

    @Autowired
    private UserInformMapper userInformMapper;

    @RequestMapping("/saveInform")
    public Map<String, Object> saveInform(String title, String content, String mobile, HttpSession session) {

        OrgAccountInfo orgAccountInfo = (OrgAccountInfo) session.getAttribute(OrgAccountInfo.ORG_SESSION_ID);

        OrgCompany orgCompany = (OrgCompany) session.getAttribute(OrgCompany.USER_COMPANY);

        UserInform userInform = new UserInform();
        if (orgCompany != null) {
            userInform.setCompanyId(orgCompany.getId());
        }
        userInform.setOrgaccoId(orgAccountInfo.getId());
        userInform.setTitle(title);
        if (StringUtils.isNotBlank(mobile)) {
            userInform.setMobile(mobile);
        }
        userInform.setContent(content);
        userInform.setState(0);
        userInform.setCreateTime(new Date());

        Map<String, Object> resultMap = new HashMap();
        userInformMapper.insert(userInform);
        if (userInform.getId() != null) {
            resultMap.put("success", true);
        } else {
            resultMap.put("success", false);
        }
        return resultMap;
    }
}
