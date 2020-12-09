package com.botosjano.springboot.employeeapp.service;

import com.botosjano.springboot.employeeapp.entity.User;
import com.botosjano.springboot.employeeapp.user.UserToRegister;

public interface UserService {

	public User findByUserName(String userName);

	public void registerUser(UserToRegister user);
	
	public String userActivation(String code);
}
