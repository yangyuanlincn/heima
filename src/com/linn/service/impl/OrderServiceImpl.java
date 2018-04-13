package com.linn.service.impl;

import java.util.List;

import com.linn.dao.OrderDao;
import com.linn.domain.Order;
import com.linn.domain.OrderItem;
import com.linn.domain.PageBean;
import com.linn.domain.User;
import com.linn.service.OrderService;
import com.linn.utils.BeanFactory;
import com.linn.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService{
	/**
	 * 添加订单
	 */
	@Override
	public void add(Order order) throws Exception {
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.添加数据到orders表中
			OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
			od.add(order);
			
			//3.添加数据到orderitem表中
			for(OrderItem oi:order.getItems()) {
				od.add2OrderItem(oi);
			}
			DataSourceUtils.commitAndClose();
		}catch(Exception e){
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
			
	}

	/**
	 * 通过订单号获取订单
	 * @return Order
	 */
	@Override
	public Order getOrderById(String oid) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		return od.getOrderById(oid);
	}

	@Override
	public void update(Order order) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}

	@Override
	public PageBean<Order> findAllByPage(User user,int currPage) throws Exception {
		//1.封装pagebean
		PageBean<Order> bean=new PageBean<>();
		bean.setCurrPage(currPage);
		int pageSize=6;//每页显示的条数
		bean.setPageSize(pageSize);
		
		//2.调用OrderDao获取List<Order> 和 总订单条数 totalcount
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		int totalCount=od.getTotalCount(user);
		List<Order> list=od.findAllByPage(user,currPage,pageSize);
		return null;
	}
	
}
