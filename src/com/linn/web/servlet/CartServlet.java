package com.linn.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linn.domain.Cart;
import com.linn.domain.CartItem;
import com.linn.domain.Product;
import com.linn.service.ProductService;
import com.linn.utils.BeanFactory;

public class CartServlet extends BaseServlet {
	
	/**
	 * 添加到购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String  add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取商品Id和数量
		String pid=request.getParameter("pid");
		int count=Integer.parseInt(request.getParameter("count"));
		
		//2.调用service获取product
		ProductService ps=(ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getByPid(pid);
		
		//3.组装CartItem
		CartItem item=new CartItem(product,count);
		
		//4.添加到购物车
		getCart(request).add2Cart(item);
		
		//5.重定向到购物车页面
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		getCart(request).clear();
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 删除一件商品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1.获取商品id
		String pid=request.getParameter("pid");
		getCart(request).removeFromCart(pid);
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	public Cart getCart(HttpServletRequest request) {
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		//判断是否存在
		if(cart==null) {
			cart=new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	
}
