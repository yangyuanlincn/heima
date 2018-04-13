package com.linn.service;

import com.linn.domain.Order;
import com.linn.domain.PageBean;
import com.linn.domain.User;

public interface OrderService {

	void add(Order order) throws Exception;

	Order getOrderById(String oid)throws Exception;

	void update(Order order)throws Exception;

	PageBean<Order> findAllByPage(User user,int currPage)throws Exception;
	
}
