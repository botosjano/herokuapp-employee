package com.botosjano.springboot.employeeapp.dao;

import com.botosjano.springboot.employeeapp.entity.User;

public interface UserDao {

    public User findByUserName(String userName);
    
    public void save(User user);
    
    User findByActivation(String code);
    
}
