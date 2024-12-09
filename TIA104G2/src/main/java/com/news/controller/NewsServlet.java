package com.news.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.news.model.*;

public class NewsServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			String str = req.getParameter("news_id");

			if (str == null || (str.trim()).length() == 0) {
				errorMsgs.add("請輸入消息編號");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			Integer news_id = null;
			try {
				news_id = Integer.valueOf(str);
			} catch (Exception e) {
				errorMsgs.add("消息編號格式不正確");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 2.開始查詢資料 *****************************************/
			NewsService newsSvc = new NewsService();
			NewsVO newsVO = newsSvc.getOneNews(news_id);
			if (newsVO == null) {
				errorMsgs.add("查無資料");
			}
			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/select_page.jsp");
				failureView.forward(req, res);
				return;// 程式中斷
			}

			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
			req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
			String url = "/back-end/emp/listOneNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
			successView.forward(req, res);
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllNews.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ****************************************/
			Integer news_id = Integer.valueOf(req.getParameter("news_id"));

			/*************************** 2.開始查詢資料 ****************************************/
			NewsService newsSvc = new NewsService();
			NewsVO newsVO = newsSvc.getOneNews(news_id);

			/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
			req.setAttribute("newsVO", newsVO); // 資料庫取出的newsVO物件,存入req
			String url = "/back-end/emp/update_news_input.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_news_input.jsp
			successView.forward(req, res);
		}

		
		if ("insert".equals(action)) { // 來自addNews.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			String news_title = req.getParameter("news_title");
			String news_titleReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
			if (news_title == null || news_title.trim().length() == 0) {
				errorMsgs.add("消息標題: 請勿空白");
			} else if (!news_title.trim().matches(news_titleReg)) { // 以下練習正則(規)表示式(regular-expression)
				errorMsgs.add("消息標題: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
			}

			String description = req.getParameter("description").trim();
			if (description == null || description.trim().length() == 0) {
				errorMsgs.add("消息內容請勿空白");
			}

			java.sql.Timestamp post_time = null;
			try {
				post_time = java.sql.Timestamp.valueOf(req.getParameter("post_time").trim());
			} catch (IllegalArgumentException e) {
				post_time = new java.sql.Timestamp(System.currentTimeMillis());
				errorMsgs.add("請輸入發布時間!");
			}
			
			java.sql.Timestamp create_time = null;
			try {
				create_time = java.sql.Timestamp.valueOf(req.getParameter("create_time").trim());
			} catch (IllegalArgumentException e) {
				create_time = new java.sql.Timestamp(System.currentTimeMillis());
				errorMsgs.add("請輸入建立時間!");
			}

//			Integer news_id = Integer.valueOf(req.getParameter("news_id").trim());

			NewsVO newsVO = new NewsVO();
			newsVO.setNews_title(news_title);
			newsVO.setDescription(description);
			newsVO.setPost_time(post_time);
			newsVO.setCreate_time(create_time);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("newsVO", newsVO); // 含有輸入格式錯誤的newsVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addNews.jsp");
				failureView.forward(req, res);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsVO = newsSvc.addNews(news_title, description, post_time, create_time);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/emp/listAllNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}
		
		if ("update".equals(action)) {
		    List<String> errorMsgs = new LinkedList<>();
		    req.setAttribute("errorMsgs", errorMsgs);

		    try {
		        // 1. 接收參數
		        String newsIdStr = req.getParameter("news_id");
		        if (newsIdStr == null || newsIdStr.trim().isEmpty()) {
		            errorMsgs.add("消息編號不得為空");
		        }
		        Integer news_id = Integer.valueOf(newsIdStr.trim());

		        String news_title = req.getParameter("news_title");
		        if (news_title == null || news_title.trim().isEmpty()) {
		            errorMsgs.add("消息標題: 請勿空白");
		        }

		        String description = req.getParameter("description");
		        if (description == null || description.trim().isEmpty()) {
		            errorMsgs.add("內容請勿空白");
		        }

		        java.sql.Timestamp post_time = null;
		        try {
		            String postTimeStr = req.getParameter("post_time");
		            if (postTimeStr == null || postTimeStr.trim().isEmpty()) {
		                throw new IllegalArgumentException("空值");
		            }
		            post_time = java.sql.Timestamp.valueOf(postTimeStr.trim());
		        } catch (Exception e) {
		            post_time = new java.sql.Timestamp(System.currentTimeMillis());
		            errorMsgs.add("請輸入有效的發布時間!");
		        }

		        java.sql.Timestamp create_time = null;
		        try {
		            String createTimeStr = req.getParameter("create_time");
		            if (createTimeStr == null || createTimeStr.trim().isEmpty()) {
		                throw new IllegalArgumentException("空值");
		            }
		            create_time = java.sql.Timestamp.valueOf(createTimeStr.trim());
		        } catch (Exception e) {
		            create_time = new java.sql.Timestamp(System.currentTimeMillis());
		            errorMsgs.add("請輸入有效的建立時間!");
		        }

		        // 2. 封裝物件
		        NewsVO newsVO = new NewsVO();
		        newsVO.setNews_id(news_id);
		        newsVO.setNews_title(news_title);
		        newsVO.setDescription(description);
		        newsVO.setPost_time(post_time);
		        newsVO.setCreate_time(create_time);

		        // 若有錯誤，返回表單頁面
		        if (!errorMsgs.isEmpty()) {
		            req.setAttribute("newsVO", newsVO);
		            RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_news_input.jsp");
		            failureView.forward(req, res);
		            return;
		        }

		        // 3. 執行更新操作
		        NewsService newsSvc = new NewsService();
		        newsVO = newsSvc.updateNews(news_id, news_title, description, post_time, create_time);

		        // 4. 成功轉交
		        req.setAttribute("newsVO", newsVO);
		        String url = "/back-end/emp/listOneNews.jsp";
		        RequestDispatcher successView = req.getRequestDispatcher(url);
		        successView.forward(req, res);

		    } catch (Exception e) {
		        errorMsgs.add("更新失敗: " + e.getMessage());
		        RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_news_input.jsp");
		        failureView.forward(req, res);
		    }
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 ***************************************/
			Integer news_id = Integer.valueOf(req.getParameter("news_id"));

			/*************************** 2.開始刪除資料 ***************************************/
			NewsService newsSvc = new NewsService();
			newsSvc.deleteNews(news_id);

			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
			String url = "/back-end/emp/listAllNews.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
			successView.forward(req, res);
		}
	}
}
