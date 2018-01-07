package com.imory.cn.news.service.impl;

import com.imory.cn.news.dao.NewsMapper;
import com.imory.cn.news.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<Map<String, Object>> listNews(int newsType, int startPos, int maxRows) {
        return newsMapper.listNews(newsType, startPos, maxRows);
    }

    @Override
    public Map<String, Object> selectById(Integer id) {
        return newsMapper.selectById(id);
    }
}
