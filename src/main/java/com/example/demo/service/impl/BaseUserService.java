package com.example.demo.service.impl;

import com.example.demo.constant.RoleConstant;
import com.example.demo.entity.UserEntity;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by jh on 2018-01-09.
 */
@Service
@Primary
@Log4j
public class BaseUserService implements UserService {

	private final UserMapper userMapper;

	public BaseUserService(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override public boolean insert(UserEntity userEntity) {
		String username = userEntity.getUsername();
		if(exits(username)) {
			return false;
		}
		userEntity.setRoles(RoleConstant.ROLE_USER);
		encryptPassword(userEntity);
		int result = userMapper.insert(userEntity);
		return 1 == result;
	}

	@Override public UserEntity getByUsername(String username) {
		return userMapper.selectByUsername(username);
	}

	/**
	 * 判断用户是否存在
	 * @param username
	 * @return
	 */
	private boolean exits(String username) {
		UserEntity userEntity = userMapper.selectByUsername(username);
		return (null != userEntity);
	}

	private void encryptPassword(UserEntity userEntity) {
		String password = userEntity.getPassword();
		password = new BCryptPasswordEncoder().encode(password);
		userEntity.setPassword(password);
	}
}
