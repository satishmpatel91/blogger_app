package org.bloggerapp.bloggerapp.exceptions;

public class InvalidArgumentException extends RuntimeException{
    private String filedName;
    private int filedValue;

    public InvalidArgumentException(String filedName, int filedValue) {
        super(String.format("Passed argument for %s:%d  is not valid",filedName,filedValue));
        this.filedName = filedName;
        this.filedValue = filedValue;
    }
}
