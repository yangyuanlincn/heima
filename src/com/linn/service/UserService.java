package com.linn.service;

import com.linn.domain.User;

public interface UserService {

	void regist(User user) throws Exception;

	User activate(String code) throws Exception;

	User login(String username, String password) throws Exception;

}
