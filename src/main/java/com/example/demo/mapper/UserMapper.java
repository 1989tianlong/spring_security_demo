package com.example.demo.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import com.example.demo.entity.UserEntity;

/**
 * Created by jh on 2018-01-09.
 */
@Mapper
@Component
public interface UserMapper {

	@Insert("insert into user(username, password, nickname, roles) values(#{username}, #{password}, #{nickname}, #{roles})")
	int insert(UserEntity userEntity);

	@Select("select * from user where username = #{username}")
	UserEntity selectByUsername(@Param("username") String username);

}