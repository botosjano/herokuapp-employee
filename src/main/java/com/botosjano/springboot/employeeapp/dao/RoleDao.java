package com.botosjano.springboot.employeeapp.dao;

import com.botosjano.springboot.employeeapp.entity.Role;

public interface RoleDao {

	public Role findRoleByName(String theRoleName);
	
}
