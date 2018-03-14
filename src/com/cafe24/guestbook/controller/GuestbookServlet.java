package com.cafe24.guestbook.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cafe24.guestbook.dao.GuestbookDao;
import com.cafe24.guestbook.vo.GuestbookVo;
import com.cafe24.mvc.util.WebUtil;

@WebServlet("/gb")
public class GuestbookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");

		String actionName = request.getParameter("a");

		if ("add".equals(actionName)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContent(content);

			GuestbookDao dao = new GuestbookDao();
			dao.insert(vo);

			WebUtil.redirect(request, response, "/guestbook2/gb");

		} else if ("delete".equals(actionName)) {
			long no = Long.parseLong(request.getParameter("no"));
			String password = request.getParameter("password");

			GuestbookVo vo = new GuestbookVo();
			vo.setNo(no);
			vo.setPassword(password);

			GuestbookDao dao = new GuestbookDao();
			dao.delete(vo);

			WebUtil.redirect(request, response, "/guestbook2/gb");

		} else if ("deleteform".equals(actionName)) {
			WebUtil.forward(request, response, "/WEB-INF/views/deleteform.jsp");

		} else {
			GuestbookDao dao = new GuestbookDao();
			List<GuestbookVo> list = dao.getList();

			request.setAttribute("list", list);

			WebUtil.forward(request, response, "/WEB-INF/views/index.jsp");

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
