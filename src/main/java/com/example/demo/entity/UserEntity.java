package com.example.demo.entity;

import lombok.Data;

/**
 * Created by jh on 2018-01-09.
 */
@Data
public class UserEntity {

	private Long id;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 权限
	 */
	private String roles;
}