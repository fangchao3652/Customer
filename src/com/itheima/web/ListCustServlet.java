package com.itheima.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cust;
import com.itheima.factory.BasicFactory;
import com.itheima.service.CustService;

public class ListCustServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CustService service = BasicFactory.getFactory().getInstance(CustService.class);
		
		System.out.println(request.getQueryString() );//  name=fangchao
		System.out.println(request.getRequestURI()  );//  /Custom/servlet/ListCustServlet
		System.out.println(request.getRequestURL() );//   http://localhost:8080/Custom/servlet/ListCustServlet
		//1.调用Service中查询所有客户的方法,查找到所有客户
		List<Cust> list = service.getAllCust();
		//2.将查找到的信息存入request域,请求转发到listCust.jsp页面进行展示
		request.setAttribute("list", list);
		request.getRequestDispatcher("/listCust.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
