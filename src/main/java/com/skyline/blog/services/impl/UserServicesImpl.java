package com.skyline.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skyline.blog.dao.UserRepo;
import com.skyline.blog.entities.User;
import com.skyline.blog.exception.ResourceNotFoundException;
import com.skyline.blog.payloads.UserDto;
import com.skyline.blog.services.UserService;

@Service
public class UserServicesImpl implements UserService{

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public UserDto createUser(UserDto userDto) {
		 User user=this.dtoToUser(userDto);
		 User savedUser=this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	   
	 User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User"," Id ",(long)userId));
	 user.setName(userDto.getName());
	 user.setEmail(userDto.getEmail());
	 user.setPassword(userDto.getPassword());
	 user.setAbout(userDto.getAbout());
	 User updateUser=this.userRepo.save(user);
	 UserDto updatedDto=this.userToDto(updateUser);
		return updatedDto;
	}

	@Override
	public UserDto getUserById(Integer userId) {
		 User user=this.userRepo.findById(userId).
				 orElseThrow(()->new ResourceNotFoundException("User"," Id ",(long)userId));
		 
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> lstUser=this.userRepo.findAll();
		
	List<UserDto> lstUserDto=lstUser.stream().map(user->userToDto(user)).collect(Collectors.toList());
		return lstUserDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		 User user=this.userRepo.findById(userId).
				 orElseThrow(()->new ResourceNotFoundException("User"," Id ",(long)userId));
		 this.userRepo.delete(user);
	}
	
	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
		return user;
	}
	
	public UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
		return userDto;
		
	}

}
