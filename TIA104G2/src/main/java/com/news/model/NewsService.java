package com.news.model;

import java.sql.Timestamp;
import java.util.List;

public class NewsService {

	private NewsDAO_interface dao;

	public NewsService() {
		dao = new NewsDAO();
	}

	public NewsVO addNews(String news_title, String description, Timestamp post_time,
			Timestamp create_time) 
	{
		NewsVO newsVO = new NewsVO();

		newsVO.setNews_title(news_title);
		newsVO.setDescription(description);
		newsVO.setPost_time(post_time);
		newsVO.setCreate_time(create_time);
		dao.insert(newsVO);

		return newsVO;
	}

	public NewsVO updateNews(Integer news_id,String news_title, String description, Timestamp post_time, 
			Timestamp create_time) {

		NewsVO newsVO = new NewsVO();

		newsVO.setNews_id(news_id);
		newsVO.setNews_title(news_title);
		newsVO.setDescription(description);
		newsVO.setPost_time(post_time);
		newsVO.setCreate_time(create_time);
		dao.update(newsVO);

		return newsVO;
	}

	public void deleteNews(Integer news_id) {
		dao.delete(news_id);
	}

	public NewsVO getOneNews(Integer news_id) {
		return dao.findByPrimaryKey(news_id);
	}
	

	public List<NewsVO> getAll() {
		return dao.getAll();
	}
}
