package com.wordweave.utils;

import java.io.IOException;
import java.io.InputStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

/**
 * Utility class for handling form data extraction from HttpServletRequest.
 */
public class FormUtils {
	/**
     * Retrieves the value of a form field from a multipart/form-data request.
     *
     * @param request   the HttpServletRequest containing the form data
     * @param fieldName the name of the form field to retrieve
     * @return the trimmed string value of the form field, or null if not found
     * @throws IOException      if an I/O error occurs while reading the input stream
     * @throws ServletException if the request is not multipart/form-data or other servlet issues occur
     */
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
