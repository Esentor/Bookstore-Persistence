package com.bookstore.persistence.converter;

import com.bookstore.persistence.dto.UserDTO;
import com.bookstore.persistence.model.User;

public class UserConverter {
	// Method to convert UserDTO to User
	public static User convertToEntity(UserDTO userDTO) {
		User user = new User();
		user.setId(userDTO.getId());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setEmail(userDTO.getEmail());
		return user;
	}

	// Method to convert User to UserDTO
	public static UserDTO convertToDTO(User user) {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());
		return userDTO;
	}
}
