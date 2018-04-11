package com.linn.service;

import java.util.List;

import com.linn.domain.Category;

public interface CategoryService {

	List<Category> findAll () throws Exception;

}
