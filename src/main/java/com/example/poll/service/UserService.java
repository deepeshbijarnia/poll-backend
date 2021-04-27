package com.example.poll.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.poll.entity.User;
import com.example.poll.exception.UserException;
import com.example.poll.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public boolean isUserPresent(User newUser) {
		boolean result = StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.anyMatch(user -> user.getEmail().equalsIgnoreCase(newUser.getEmail()));
		return result;
	}

	public boolean addUser(User newUser) {
		boolean result = false;
		User user = userRepository.save(newUser);
		if (user != null) {
			result = true;
		}
		return result;
	}

	public User findUser(User findUser) throws UserException {
		User user = null;
		Predicate<User> emailMatch = usr -> usr.getEmail().equalsIgnoreCase(findUser.getEmail());
		Predicate<User> pwdMatch = usr -> usr.getPassword().equals(findUser.getPassword());
		List<User> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
				.filter(emailMatch.and(pwdMatch)).collect(Collectors.toList());
		if (users != null && !users.isEmpty()) {
			user = users.get(0);
			return user;
		} else {
			throw new UserException("User not found");
		}
	}

	public User findUser(Long userId) throws UserException {
		Optional<User> optUser = userRepository.findById(userId);
		if (optUser.isPresent()) {
			return optUser.get();
		}
		return null;
	}
	
	public User findUser(String email) throws UserException {
		Optional<User> optUser = userRepository.findByEmail(email);
		if (optUser.isPresent()) {
			return optUser.get();
		}
		return null;
	}
}
