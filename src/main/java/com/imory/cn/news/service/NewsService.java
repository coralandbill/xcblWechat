package com.imory.cn.news.service;

import java.util.List;
import java.util.Map;

/**
 * <p>名称</p>
 *
 * @author zb.jiang
 * @version 1.0
 * @Date 2018/1/7
 */
public interface NewsService {

    List<Map<String, Object>> listNews(int newsType, int startPos, int maxRows);

    Map<String, Object> selectById(Integer id);

}
