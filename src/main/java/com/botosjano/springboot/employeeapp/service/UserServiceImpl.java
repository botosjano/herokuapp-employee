package com.botosjano.springboot.employeeapp.service;

import java.util.Arrays;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.botosjano.springboot.employeeapp.dao.RoleDao;
import com.botosjano.springboot.employeeapp.dao.UserDao;
import com.botosjano.springboot.employeeapp.entity.User;
import com.botosjano.springboot.employeeapp.user.UserToRegister;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	private final Log log = LogFactory.getLog(this.getClass());

	// need to inject user dao
	private UserDao userDao;
	private RoleDao roleDao;
	private BCryptPasswordEncoder passwordEncoder;
	private EmailService emailService;

	@Autowired
	public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder,
			EmailService emailService) {
		this.userDao = userDao;
		this.roleDao = roleDao;
		this.passwordEncoder = passwordEncoder;
		this.emailService = emailService;
	}

	@Override
	@Transactional
	public User findByUserName(String userName) {
		// check the database if the user already exists
		return userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public void registerUser(UserToRegister userToregister) {
		User user = new User();
		// assign user details to the user object
		user.setUserName(userToregister.getUserName());
		user.setPassword(passwordEncoder.encode(userToregister.getPassword()));
		user.setFirstName(userToregister.getFirstName());
		user.setLastName(userToregister.getLastName());
		user.setEmail(userToregister.getEmail());

		// give user default role of "employee"
		user.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_EMPLOYEE")));

		user.setEnabled(false);
		user.setActivation(generateKey());

		String code = user.getActivation();
		// save user in the database
		userDao.save(user);

		String username = user.getUserName();
		// send email
		emailService.sendMessage(user.getEmail(), code, username);
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = findByUserName(userName);

		if (user == null) {
			throw new UsernameNotFoundException("he user is not activated yet.");
		}

		return new UserDetailsImpl(user);

		
	}

	@Override
	@Transactional
	public String userActivation(String code) {
		
		User user = userDao.findByActivation(code);
		
		if (user == null)
			return "noresult";

		user.setEnabled(true);
		user.setActivation("");
		
		userDao.save(user);
		
		return "ok";
	}

	// generate code for activation
	
	public String generateKey() {
		Random random = new Random();
		char[] word = new char[16];
		
		for (int j = 0; j < word.length; j++) {
			word[j] = (char) ('a' + random.nextInt(26));
		}
		
		String result = new String(word);
		log.debug("random code: " + result);
		
		return new String(word);
	}
}
