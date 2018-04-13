package com.linn.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Order implements Serializable{
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
	private List<OrderItem> items=new LinkedList<>();
	private String oid;
	private String ordertime;
	private Double total=0.0;
	private Integer state;//订单支付状态0.未支付  1.已支付
	private String address;
	private String name;
	private String telephone;
	private User user;//属于哪个用户
	
	public List<OrderItem> getItems() {
		return items;
	}
	public void setItem(List<OrderItem> items) {
		this.items = items;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public String getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
