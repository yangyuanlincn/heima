package com.linn.web.servlet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linn.utils.PaymentUtil;
import com.linn.domain.Cart;
import com.linn.domain.CartItem;
import com.linn.domain.Order;
import com.linn.domain.OrderItem;
import com.linn.domain.PageBean;
import com.linn.domain.User;
import com.linn.service.OrderService;
import com.linn.utils.BeanFactory;
import com.linn.utils.UUIDUtils;

public class OrderServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * 添加到订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.判断用户是否登路
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "jsp/login.jsp";
		}

		// 2.创建订单
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		Order order = new Order();
		order.setOid(UUIDUtils.getId());
		order.setTotal(cart.getTotal());
		order.setUser(user);
		order.setState(0);
		order.setOrdertime(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
		// 3.封装OrderItem
		for (CartItem cItem : cart.getItems()) {
			OrderItem oItem = new OrderItem();
			oItem.setCount(cItem.getCount());
			oItem.setProduct(cItem.getProduct());
			oItem.setSubtotal(cItem.getSubtotal());
			oItem.setOrder(order);
			oItem.setItemid(UUIDUtils.getId());
			order.getItems().add(oItem);
		}

		// 4.调用service 添加订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);

		// 5.清空购物车
		cart.clear();
		request.getSession().setAttribute("cart", cart);

		// 6.把order放入request域中，请求转发
		request.setAttribute("order", order);
		return "jsp/order_info.jsp";

	}

	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1.获取参数
		String toaddress = request.getParameter("toaddress");
		String toname = request.getParameter("toname");// 收件人姓名
		String totelphone = request.getParameter("totelphone");// 收件人电话
		String oid = request.getParameter("oid");// 订单号
		System.out.println(toaddress+toname+totelphone);
		// 2.通过oid获取order
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getOrderById(oid);

		// 3.更新order表
		order.setAddress(toaddress);
		order.setName(toname);
		order.setTelephone(totelphone);
		os.update(order);

		// 组织发送支付公司需要哪些数据
		String pd_FrpId = request.getParameter("pd_FrpId");// 银行名称
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
				p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

		// 发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);

		response.sendRedirect(sb.toString());
		return null;

	}

	/**
	 * 支付完成后回调函数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid,
				r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:" + r6_Order + ",金额为:" + r3_Amt + "已经支付成功,等待发货~~");

			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}

			// 修改订单状态
			OrderService s = (OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getOrderById(r6_Order);
			order.setState(1);

			s.update(order);

		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}

		return "/jsp/msg.jsp";
	}
	
	/**
	 * 分页查询显示所有订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.判断用户是否登录
		User user=(User) request.getSession().getAttribute("user");
		if(user==null) {
			return "jsp/login.jsp";
		}
		//1.1获取当前页
		int currPage=Integer.parseInt(request.getParameter("currPage"));
		
		//2.调用service 返回pagebean
		OrderService os=(OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> bean=os.findAllByPage(user,currPage);
		
		//3.将pagebean放入request域中，请求转发
		request.setAttribute("bean", bean);
		return "jsp/order_list.jsp";
	}

}
