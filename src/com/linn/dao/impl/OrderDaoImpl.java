package com.linn.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.linn.dao.OrderDao;
import com.linn.domain.Order;
import com.linn.domain.OrderItem;
import com.linn.domain.Product;
import com.linn.domain.User;
import com.linn.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {
	/**
	 * 添加到orders表
	 */
	@Override
	public void add(Order order) throws Exception {
		/*
		 * `oid` varchar(32) NOT NULL,
		  `ordertime` datetime DEFAULT NULL,
		  `total` double DEFAULT NULL,
		  
		  `state` int(11) DEFAULT NULL,
		  `address` varchar(30) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  
		  `telephone` varchar(20) DEFAULT NULL,
		  `uid` varchar(32) DEFAULT NULL,
		 */
		QueryRunner qr=new QueryRunner();//确保事务中使用的是同一个连接
		String sql="insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql,order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	/**
	 * 添加到orderitem表
	 */
	@Override
	public void add2OrderItem(OrderItem oi) throws Exception {
		/**
		 * `itemid` varchar(32) NOT NULL,
		  `count` int(11) DEFAULT NULL,
		  `subtotal` double DEFAULT NULL,
		  `pid` varchar(32) DEFAULT NULL,
		  `oid` varchar(32) DEFAULT NULL,
		 */
		QueryRunner qr=new QueryRunner();
		String sql="insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
	}
	
	/**
	 * 通过订单号查询订单
	 * @return Order
	 */
	@Override
	public Order getOrderById(String oid) throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		//1.获取订单除List<OrderItem>之外的信息
		String sql="select * from orders where oid=?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class));
		
		//2.封装OrderItem
		String sql_2="selet * from orderitem,product where orderitem.pid=product.pid and orderitem.oid=?";
		List<Map<String, Object>> list = qr.query(sql, new MapListHandler(),oid);
		for(Map<String,Object> map:list) {
			//2.1封装orderitem除product之外的信息
			OrderItem oi=new OrderItem();
			BeanUtils.populate(oi,map);
			//2.2封装product
			Product p=new Product();
			BeanUtils.populate(p, map);
			//2.3设置product和order
			oi.setProduct(p);
			oi.setOrder(order);
			//2.4将orderitem加入order的items
			order.getItems().add(oi);
		}
		return null;
	}

	
	/**
	 * 更新orders表
	 */
	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/*
		 * `oid` varchar(32) NOT NULL,
		  `ordertime` datetime DEFAULT NULL,
		  `total` double DEFAULT NULL,
		  
		  `state` int(11) DEFAULT NULL,
		  `address` varchar(30) DEFAULT NULL,
		  `name` varchar(20) DEFAULT NULL,
		  
		  `telephone` varchar(20) DEFAULT NULL,
		  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql="update orders set oid=?,ordertime=?,total=?,state=?,address=?,name=?,telphone=?,uid=?";
		qr.update(sql, order.getOid(),order.getOrdertime(),order.getTotal(),
				order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()
				);
	}

	/**
	 * 根据 user查询 订单总数
	 * @return Integer 订单条数
	 */
	@Override
	public Integer getTotalCount(User user) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select count(*) from orders where uid=?";
		return ((Long) qr.query(sql, new ScalarHandler(),user.getUid())).intValue();
	}

	/**
	 * 分页查询订单
	 * @return List<Order>
	 */
	@Override
	public List<Order> findAllByPage(User user, int currPage,int pageSize) throws Exception {
		QueryRunner qr=new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from orders where uid=? order by ordertime desc limit ?,?";
		//1.从orders表中查询用户的所有订单信息，封装成List<Order>集合
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class),user.getUid(),(currPage-1)*pageSize,pageSize);

		String sql_2="select * from orderitem,product where orderitem.pid=product.pid and orderitem.oid=?";
		
		for(Order order:list) {
			//2.从orderitem和product表中遍历查询每个订单的订单项,每个订单项中 key为字段  value为值
			List<Map<String, Object>> pList = qr.query(sql_2, new MapListHandler(),order.getOid());
			for(Map<String,Object> map:pList) {
				//3.封装product
				Product p=new Product();
				BeanUtils.populate(p, map);
				
				//4.封装OrderItem
				OrderItem oi=new OrderItem();
				BeanUtils.populate(oi, map);
				//5.设置每个OrderItem的product
				oi.setProduct(p);
				//6.将OrderItem加入 Order的Items中
				order.getItems().add(oi);
			}
		}
		return list;
	}

}
