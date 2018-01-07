package com.imory.cn.news.controller;

import com.imory.cn.news.service.NewsService;
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
@RequestMapping("/wechat/newsAjax")
@RestController
public class NewsAjaxController {

    @Autowired
    private NewsService newsService;

    @RequestMapping("/list")
    public Map list(int newsType, int startPos, int maxRows) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> newsList = newsService.listNews(newsType, startPos, maxRows + 1);
        if (newsList != null && newsList.size() > maxRows) {
            newsList.remove(newsList.size() - 1);
            map.put("hasMore", true);
        } else {
            map.put("hasMore", false);
        }
        map.put("success", true);
        map.put("newsList", newsList);
        return map;
    }
}
