package com.bookstore.persistence.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.persistence.converter.UserConverter;
import com.bookstore.persistence.dto.UserDTO;
import com.bookstore.persistence.model.User;
import com.bookstore.persistence.repository.UserDAO;

@Service
public class UserService {

	@Autowired
	private final UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public List<UserDTO> getAllUsers() {
		List<User> users = userDAO.findAll();
		return users.stream().map(UserConverter::convertToDTO).collect(Collectors.toList());
	}

	public UserDTO getUserById(Long userId) {
		Optional<User> optionalUser = userDAO.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			return UserConverter.convertToDTO(user);
		}
		return null; // Or throw an exception indicating the user was not found
	}

	public UserDTO createUser(UserDTO userDTO) {
		User user = UserConverter.convertToEntity(userDTO);
		User savedUser = userDAO.save(user);
		return UserConverter.convertToDTO(savedUser);
	}

	public UserDTO updateUser(UserDTO userDTO) {
		Optional<User> optionalUser = userDAO.findById(userDTO.getId());
		if (optionalUser.isPresent()) {
			User existingUser = optionalUser.get();
			// Update the properties of existingUser with the ones from userDTO
			existingUser.setFirstName(userDTO.getFirstName());
			existingUser.setLastName(userDTO.getLastName());
			existingUser.setEmail(userDTO.getEmail());
			// Save the updated user
			User updatedUser = userDAO.save(existingUser);
			return UserConverter.convertToDTO(updatedUser);
		}
		return null; // Or throw an exception indicating the user was not found
	}

	public boolean deleteUser(Long userId) {
		User existingUser = userDAO.findById(userId).orElse(null);
		if (existingUser == null) {
			return false;
		}
		userDAO.delete(existingUser);
		return true;
	}

}
