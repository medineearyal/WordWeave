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

public class ImageUtil {
	
	public boolean uploadImage(HttpServletRequest req, String field) throws IOException, ServletException {
		Part image = req.getPart(field);
		if (image == null || image.getSize() == 0) {
	        System.out.println("No file uploaded.");
	        return false;
	    }

	    String uploadPath = req.getServletContext().getRealPath("/images");

	    // Create the folder if it doesn't exist
	    File uploadDir = new File(uploadPath);
	    if (!uploadDir.exists()) {
	        uploadDir.mkdirs();
	    }

	    // Get original file name
	    String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();

	    // Create file on disk
	    File file = new File(uploadDir, fileName);

	    try (InputStream input = image.getInputStream()) {
	        Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	    }

	    System.out.println("File saved to: " + file.getAbsolutePath());
	    return true;
	}

}
