package com.kuki.social_networking.util.validator;

public class FieldValidator {

    public static String validateFullName(String fullName) {
        if(fullName == null || fullName.trim().isEmpty()) {
            return null;
        }

        if(fullName.length() > 100) {
            throw new IllegalArgumentException("Full name is too long");
        }

        if(fullName.length() < 5) {
            throw new IllegalArgumentException("Full name is too short");
        }

        return fullName.toLowerCase().trim();
    }

    public static String validateString(String name){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Name is required");
        }
        return name.toLowerCase().trim();
    }

    public static String validatePassword(String password) {
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

        if(password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        if(!password.matches(regex)) {
            throw new IllegalArgumentException("Password is not secure enough");
        }

        return password;
    }
}
