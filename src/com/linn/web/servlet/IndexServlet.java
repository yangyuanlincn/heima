package com.linn.web.servlet;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linn.domain.Category;
import com.linn.domain.Product;
import com.linn.service.CategoryService;
import com.linn.service.ProductService;
import com.linn.service.impl.CategoryServiceImpl;
import com.linn.service.impl.ProductServiceImpl;
import com.linn.utils.BeanFactory;
import com.linn.utils.DataSourceUtils;

/**
 * 和首页相关的servlet
 */
public class IndexServlet extends BaseServlet {
	public String index(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//去数据库中查询最新商品和热门商品  将他们放入request域中 请求转发
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		
		//最新商品
		List<Product> newList=null;
		List<Product> hotList=null;
		try {
			newList = ps.findNew();
			hotList=ps.findHot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//热门商品
		
		//将俩个list放入域中
		request.setAttribute("nList", newList);
		request.setAttribute("hList", hotList);
		return "/jsp/index.jsp";
	}

}