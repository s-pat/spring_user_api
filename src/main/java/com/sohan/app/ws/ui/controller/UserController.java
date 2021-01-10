package com.sohan.app.ws.ui.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import com.sohan.app.ws.exceptions.UserServiceException;
import com.sohan.app.ws.service.UserService;
import com.sohan.app.ws.shared.dto.UserDto;
import com.sohan.app.ws.ui.model.request.UserDetailsRequestModel;
import com.sohan.app.ws.ui.model.response.ErrorMessages;
import com.sohan.app.ws.ui.model.response.UserRest;

@RestController
@RequestMapping("users")
public class UserController {

	@Autowired
	UserService userService;
	
	
	@GetMapping(path="/{id}", produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserRest getUser(@PathVariable String id) 
	{
		UserRest result = new UserRest();
		UserDto userDTO = userService.getUserByUserId(id);
		BeanUtils.copyProperties(userDTO, result);
		
		return result;
	}
	
	@PostMapping
	(
			consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
			produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }
	)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception 
	{
		UserRest result = new UserRest();
		if(userDetails.getFirstName().isEmpty() || userDetails.getFirstName().isBlank()) throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
		
		
		UserDto userDto = new UserDto();
		
		BeanUtils.copyProperties(userDetails, userDto);
	
		UserDto createUser = userService.createUser(userDto);
		
		BeanUtils.copyProperties(createUser, result);
		
		return result;
		
	}
	
	@PutMapping
	public String updateUser() {
		return "update user called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "delete user called";
	}
	
	
}
