<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ page import="com.news.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  NewsVO newsVO = (NewsVO) request.getAttribute("newsVO"); //NewsServlet.java(Concroller), 存入req的newsVO物件
%>

<html>
<head>
<title>消息資料 - listOneNews.jsp</title>

<style>
  table#table-1 {
	background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
  }
  table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
  }
  h4 {
    color: blue;
    display: inline;
  }
</style>

<style>
  table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>消息資料 - listOneNews.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/blackhole.jpg" width="100" height="40" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>消息ID</th>
		<th>消息標題</th>
		<th>消息內容</th>
		<th>發布時間</th>
		<th>建立時間</th>
	</tr>
	<tr>
		<td><%=newsVO.getNews_id()%></td>
		<td><%=newsVO.getNews_title()%></td>
		<td><%=newsVO.getDescription()%></td>
		<td><%=newsVO.getPost_time()%></td>
		<td><%=newsVO.getCreate_time()%></td>
	</tr>
</table>

</body>
</html>