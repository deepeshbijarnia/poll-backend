package com.example.poll.controller;

import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.poll.entity.User;
import com.example.poll.exception.UserException;
import com.example.poll.request.UserRequest;
import com.example.poll.response.UserResponse;
import com.example.poll.service.ImageService;
import com.example.poll.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private ImageService imageService;
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping(value = "/register")
	UserResponse registerUser(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = new UserResponse();
		try {
			boolean result = false;
			User newUser = modelMapper.map(userRequest, User.class);
			if (userService.isUserPresent(newUser)) {
				throw new UserException("User already exists");
			}
			newUser.setImageSrc("http://localhost:8080/user/getImage/man01.png");
			result = userService.addUser(newUser);
			if (result) {
				userResponse = modelMapper.map(newUser, UserResponse.class);
				userResponse.setServerMessage("User created successfully");
				return userResponse;
			} else {
				throw new UserException("Failed to add user");
			}
		} catch (Exception e) {
			userResponse.setServerMessage(e.getMessage());
			return userResponse;
		}
	}

	@PostMapping("/login")
	ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = null;
		try {
			User user = modelMapper.map(userRequest, User.class);
			User loggedInUser = userService.findUser(user);
			if (loggedInUser != null) {
				userResponse = modelMapper.map(loggedInUser, UserResponse.class);
				userResponse.setServerMessage("User found");
				return new ResponseEntity<>(userResponse, HttpStatus.OK);

			} else {
				throw new Exception("User not found");
			}
		} catch (Exception e) {
			userResponse = new UserResponse();
			userResponse.setServerMessage(e.getMessage());
			return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/get-user")
	ResponseEntity<UserResponse> getUserProfile(@RequestParam String email) {
		UserResponse userResponse = null;
		try {
			User user = userService.findUser(email);
			if (user != null) {
				userResponse = modelMapper.map(user, UserResponse.class);
				userResponse.setServerMessage("User found");
				return new ResponseEntity<>(userResponse, HttpStatus.OK);
			} else {
				throw new Exception("User not found");
			}
		} catch (Exception e) {
			userResponse = new UserResponse();
			userResponse.setServerMessage(e.getMessage());
			return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/update")
	UserResponse updateUser(@RequestBody UserRequest userRequest) {
		UserResponse userResponse = new UserResponse();
		try {
			boolean result = false;
			User newUser = modelMapper.map(userRequest, User.class);
			if (!userService.isUserPresent(newUser)) {
				throw new UserException("User details not present! Contact Admin!");
			}
			result = userService.addUser(newUser);
			if (result) {
				userResponse = modelMapper.map(newUser, UserResponse.class);
				userResponse.setServerMessage("User updated successfully");
				return userResponse;
			} else {
				throw new UserException("Failed to update user details");
			}
		} catch (Exception e) {
			userResponse.setServerMessage(e.getMessage());
			return userResponse;
		}
	}

	@PostMapping("/upload")
	UserResponse uploadImage(@RequestParam MultipartFile file, @RequestParam String filename, @RequestParam String id) {
		UserResponse userResponse = new UserResponse();
		try {
			User user = userService.findUser(Long.valueOf(id));
			if (file != null) {
				user = imageService.uploadImage(file, filename, user);
				userResponse = modelMapper.map(user, UserResponse.class);
				userResponse.setServerMessage("Image Uploaded");
			} else {
				userResponse.setImageSrc("http://localhost:8080/user/getImage/man01.png");
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			userResponse.setServerMessage(e.getMessage());
		}
		return userResponse;
	}

	@GetMapping(value = "getImage/{imageName:.+}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public @ResponseBody byte[] getImageWithMediaType(@PathVariable(name = "imageName") String fileName)
			throws IOException {
		return this.imageService.getImageWithMediaType(fileName);
	}

}
