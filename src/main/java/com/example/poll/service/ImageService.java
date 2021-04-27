package com.example.poll.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.poll.entity.User;
import com.example.poll.exception.ImageException;

@Service
public class ImageService {
	Path storageDirectory = Paths.get("src", "main", "resources", "profileimage");
	String storageDirectoryPath = storageDirectory.toString();

	public User uploadImage(MultipartFile file, String filename, User user) throws ImageException {
		try {
			String fileName = "Poll-" + user.getId() + "-"+filename;
			// String fileName = StringUtils.cleanPath(file.getOriginalFilename());
			if (Files.exists(storageDirectory)) {
				Path destination = Paths.get(storageDirectoryPath + "\\" + fileName);
				Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
				String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("user/getImage/")
						.path(fileName).toUriString();
				user.setImageSrc(fileDownloadUri);
//				// return the download image url as a response entity
//				return ResponseEntity.ok(fileDownloadUri);
			} else {
				throw new ImageException("Image Storage Path does not exist");
			}
		} catch (ImageException | IOException e) {
			System.err.println(e.getMessage());
			throw new ImageException("Image Upload failed. Choose another picture");
		}
		return user;
	}

	public byte[] getImageWithMediaType(String imageName) throws IOException {
		Path destination = Paths.get(storageDirectoryPath + "\\" + imageName);// retrieve the image by its name
		return IOUtils.toByteArray(destination.toUri());
	}
}
