package com.linn.domain;

public class CartItem {
	private Product product;
	private Double subtotal=0.0;
	private int count;//购买数量
	public CartItem() {}
	public CartItem(Product product,int count) {
		this.product=product;
		this.count=count;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getSubtotal() {
		return product.getShop_price()*count;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
