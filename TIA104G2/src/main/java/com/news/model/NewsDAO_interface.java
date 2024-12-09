package com.news.model;

import java.util.*;

public interface NewsDAO_interface {
          public void insert(NewsVO newsVO);
          public void update(NewsVO newsVO);
          public void delete(Integer news_id);
          public NewsVO findByPrimaryKey(Integer news_id);
          public List<NewsVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<NewsVO> getAll(Map<String, String[]> map); 
}
