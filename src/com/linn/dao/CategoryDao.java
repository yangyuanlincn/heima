package com.linn.dao;

import java.util.List;

import com.linn.domain.Category;

public interface CategoryDao {

	List<Category> findAll () throws Exception;

}
