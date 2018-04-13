package com.linn.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart implements Serializable{
	private Map<String,CartItem> map=new LinkedHashMap<>();//key为商品id
	private Double total=0.0;//总金额
	
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
	/**
	 * 添加到购物车
	 * @param item
	 */
	public void add2Cart(CartItem item) {
		//1.先判断购物车是否有该商品
		String pid=item.getProduct().getPid();
		if(map.containsKey(pid)) {
			CartItem tItem=map.get(pid);
			tItem.setCount(item.getCount()+tItem.getCount());
		}else {
			map.put(pid, item);
		}
		
		//2.设置总金额
		total=total+item.getSubtotal();
	}
	
	/**
	 * 清空购物车，金额置0
	 */
	public void clear() {
		map.clear();
		//金额置为0
		total=0.0;
	}
	
	/**
	 * 删除一件商品
	 * @param pid
	 */
	public void removeFromCart(String pid) {
		//从map中删除
		CartItem item=map.get(pid);
		map.remove(pid);
		//减去总金额
		total=total-item.getSubtotal();
	}
	
	public Collection<CartItem> getItems(){
		return map.values();
	}
	
}
