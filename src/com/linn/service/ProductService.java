package com.linn.service;

import java.util.List;

import com.linn.domain.PageBean;
import com.linn.domain.Product;

public interface ProductService {

	List<Product> findNew() throws Exception;

	List<Product> findHot() throws Exception;

	Product getByPid(String pid) throws Exception;

	PageBean<Product> findByPage(int currPage, int pageSize, String cid) throws Exception;

}
