package org.bloggerapp.bloggerapp.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String entityName, String filedName, int filedValue) {
        super(String.format("%s not found with %s : %d", entityName, filedName, filedValue));
    }

    public ResourceNotFoundException(String entityName, String filedName, String filedValue) {
        super(String.format("%s not found with %s : %s", entityName, filedName, filedValue));
    }
}
