package com.linn.dao;

import java.util.List;

import com.linn.domain.Order;
import com.linn.domain.OrderItem;
import com.linn.domain.User;

public interface OrderDao {

	void add(Order order) throws Exception;

	void add2OrderItem(OrderItem oi)throws Exception;

	Order getOrderById(String oid)throws Exception;

	void update(Order order)throws Exception;

	Integer getTotalCount(User user)throws Exception;

	List<Order> findAllByPage(User user, int currPage, int pageSize) throws Exception;

}
