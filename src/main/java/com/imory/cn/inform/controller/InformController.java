package com.imory.cn.inform.controller;

import com.imory.cn.annotation.SessionCheck;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Controller
@RequestMapping("/wechat/inform")
public class InformController {

    @SessionCheck
    @RequestMapping("/index")
    public String index()
    {
        return null;
    }
}
