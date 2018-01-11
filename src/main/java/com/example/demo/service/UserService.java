package com.example.demo.service;

import com.example.demo.entity.UserEntity;

/**
 * Created by jh on 2018-01-09.
 */
public interface UserService {
	/**
	 * 添加新用户
	 *
	 * username 唯一， 默认 USER 权限
	 */
	boolean insert(UserEntity userEntity);

	/**
	 * 查询用户信息
	 * @param username 账号
	 * @return UserEntity
	 */
	UserEntity getByUsername(String username);
}
