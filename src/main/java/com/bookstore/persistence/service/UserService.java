package com.bookstore.persistence.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public UserDTO createUser(User user) {
		return toUserDTO(userDAO.save(user));
	}

	public List<UserDTO> getAllUsers() {
		List<User> users = userDAO.findAll();
		return users.stream().map(user -> toUserDTO(user)).collect(Collectors.toList());
	}

	public UserDTO getUserById(Long id) {
		return toUserDTO(userDAO.findById(id).orElse(null));
	}

	public UserDTO updateUser(Long userId, User updatedUser) {
		User existingUser = userDAO.findById(userId).orElse(null);
		if (existingUser == null) {
			return null;
		}
		BeanUtils.copyProperties(updatedUser, existingUser, "id");
		return toUserDTO(userDAO.save(existingUser));
	}

	public boolean deleteUser(Long id) {
		User existingUser = userDAO.findById(id).orElse(null);
		if (existingUser == null) {
			return false;
		}
		userDAO.delete(existingUser);
		return true;
	}

	private UserDTO toUserDTO(User user) {
		if (user == null) {
			return null;
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmail(user.getEmail());

		return userDTO;
	}
}
