package com.skyline.blog.services;

import java.util.List;

import com.skyline.blog.payloads.UserDto;

public interface UserService {

	public UserDto registerNewUser(UserDto user);
	public UserDto createUser(UserDto user);
	public UserDto updateUser(UserDto user, Integer userId);
	public UserDto getUserById(Integer userId);
	public List<UserDto>  getAllUsers();
	public void deleteUser(Integer userId);
}
