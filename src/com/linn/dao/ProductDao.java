package com.linn.dao;

import java.util.List;

import com.linn.domain.Product;

public interface ProductDao {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getByPid(String pid) throws Exception;

	List<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

	int getTotalCount(String cid) throws Exception;

}
