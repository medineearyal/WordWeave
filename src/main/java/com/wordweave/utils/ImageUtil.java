package com.wordweave.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling image upload operations in web applications.
 * Provides methods to process file uploads from HTTP requests and save them to the server.
 */
public class ImageUtil {
	/**
     * Uploads an image file from a multipart HTTP request to the server's image directory.
     * 
     * @param req The HttpServletRequest containing the file upload
     * @param field The name of the form field containing the file
     * @return true if the upload was successful, false if no file was provided
     * @throws IOException If an I/O error occurs during file operations
     * @throws ServletException If the request is not a multipart request or other servlet error occurs
     */
	public boolean uploadImage(HttpServletRequest req, String field) throws IOException, ServletException {
		Part image = req.getPart(field);
		if (image == null || image.getSize() == 0) {
	        System.out.println("No file uploaded.");
	        return false;
	    }

	    String uploadPath = req.getServletContext().getRealPath("/images");

	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();

	    File file = new File(uploadDir, fileName);

	    try (InputStream input = image.getInputStream()) {
	        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    }

	    return true;
	}

}
