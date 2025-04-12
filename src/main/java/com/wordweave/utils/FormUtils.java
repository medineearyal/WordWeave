package com.wordweave.utils;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FormUtils {
	public static String getFormField(HttpServletRequest request, String fieldName) throws IOException, ServletException {
	    Part part = request.getPart(fieldName);
	    if (part != null) {
	        try (InputStream input = part.getInputStream()) {
	            return new String(input.readAllBytes()).trim();
	        }
	    }
	    return null;
	}

}
