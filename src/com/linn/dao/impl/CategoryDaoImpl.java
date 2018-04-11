package com.linn.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.linn.dao.CategoryDao;
import com.linn.domain.Category;
import com.linn.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="select * from category";
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

}
