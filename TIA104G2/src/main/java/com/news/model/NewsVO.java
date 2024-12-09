package com.news.model;
import java.sql.Timestamp;

public class NewsVO implements java.io.Serializable{
	private Integer news_id;
	private String news_title;
	private String description;
	private Timestamp post_time;
	private Timestamp create_time;
	public Integer getNews_id() {
		return news_id;
	}
	public void setNews_id(Integer news_id) {
		this.news_id = news_id;
	}
	public String getNews_title() {
		return news_title;
	}
	public void setNews_title(String news_title) {
		this.news_title = news_title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getPost_time() {
		return post_time;
	}
	public void setPost_time(Timestamp post_time) {
		this.post_time = post_time;
	}
	public Timestamp getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Timestamp create_time) {
		this.create_time = create_time;
	}
	
}
