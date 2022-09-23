package br.com.alura.school.exceptions;

public class UserRoleValidationException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserRoleValidationException(String message) {
        super(message);
    }
}