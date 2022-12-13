package org.bloggerapp.bloggerapp.services;

import org.bloggerapp.bloggerapp.payloads.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    public UserDto registerNewUser(UserDto userDto);
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto,int id);
    public UserDto getUserById(int userid);
    public List<UserDto> getAllUser();
    public boolean deleteUser(int userid);
}
