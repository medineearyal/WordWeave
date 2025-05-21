package com.wordweave.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

import jakarta.servlet.http.Part;

/**
 * Utility class for validating various types of input data.
 * Provides methods for common validation scenarios including strings, emails,
 * passwords, phone numbers, images, and date-related validations.
 */
public class ValidationUtil {
	/**
     * Checks if a string is null or empty (after trimming whitespace).
     * 
     * @param value The string to validate
     * @return true if the string is null or empty, false otherwise
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates if a string contains only alphabetic characters (a-z, A-Z).
     * 
     * @param value The string to validate
     * @return true if the string contains only letters, false otherwise
     */
    public static boolean isAlphabetic(String value) {
        return value != null && value.matches("^[a-zA-Z]+$");
    }

    /**
     * Validates if a string is alphanumeric and starts with a letter.
     * 
     * @param value The string to validate
     * @return true if the string starts with a letter and contains only letters and numbers, false otherwise
     */
    public static boolean isAlphanumericStartingWithLetter(String value) {
        return value != null && value.matches("^[a-zA-Z][a-zA-Z0-9]*$");
    }

    /**
     * Validates if a string represents a valid gender ("male" or "female", case-insensitive).
     * 
     * @param value The gender string to validate
     * @return true if the value is "male" or "female" (any case), false otherwise
     */
    public static boolean isValidGender(String value) {
        return value != null && (value.equalsIgnoreCase("male") || value.equalsIgnoreCase("female"));
    }

    /**
     * Validates an email address format using standard email pattern.
     * 
     * @param email The email address to validate
     * @return true if the email matches standard format, false otherwise
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates a Nepali phone number format (starts with 98 followed by 8 digits).
     * 
     * @param number The phone number to validate
     * @return true if the number starts with 98 and has 10 total digits, false otherwise
     */
    public static boolean isValidPhoneNumber(String number) {
        return number != null && number.matches("^98\\d{8}$");
    }

    /**
     * Validates password strength requirements:
     * - At least 8 characters long
     * - Contains at least one uppercase letter
     * - Contains at least one digit
     * - Contains at least one special character
     * 
     * @param password The password to validate
     * @return true if the password meets all requirements, false otherwise
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password != null && password.matches(passwordRegex);
    }

    /**
     * Validates if an uploaded image file has an allowed extension (.jpg, .jpeg, .png, .gif).
     * 
     * @param imagePart The uploaded file part to validate
     * @return true if the file has a valid image extension, false otherwise
     */
    public static boolean isValidImageExtension(Part imagePart) {
        if (imagePart == null || isNullOrEmpty(imagePart.getSubmittedFileName())) {
            return false;
        }
        String fileName = imagePart.getSubmittedFileName().toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
    }

    /**
     * Validates if two password strings match each other.
     * 
     * @param password The original password
     * @param retypePassword The password to compare against
     * @return true if both passwords are non-null and equal, false otherwise
     */
    public static boolean doPasswordsMatch(String password, String retypePassword) {
        return password != null && password.equals(retypePassword);
    }

    /**
     * Validates if a date of birth indicates the person is at least 16 years old.
     * 
     * @param dob The date of birth to validate
     * @return true if the age is 16 or older, false otherwise or if dob is null
     */
    public static boolean isAgeAtLeast16(LocalDate dob) {
        if (dob == null) {
            return false;
        }
        LocalDate today = LocalDate.now();
        return Period.between(dob, today).getYears() >= 16;
    }
}
