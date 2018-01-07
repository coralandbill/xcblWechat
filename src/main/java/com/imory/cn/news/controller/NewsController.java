package com.imory.cn.news.controller;

import com.imory.cn.annotation.SessionCheck;
import com.imory.cn.news.service.NewsService;
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
@RequestMapping("/wechat/news")
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/index")
    @SessionCheck
    public String index(Integer newsType, Model model) {
        newsType = newsType == null ? 1 : newsType;
        model.addAttribute("newsType", newsType);
        return null;
    }

    @RequestMapping("/detail")
    public String detail(Integer id, Model model) {
        Map<String, Object> newsDetail = newsService.selectById(id);
        model.addAttribute("newsDetail", newsDetail);
        return null;
    }

}
