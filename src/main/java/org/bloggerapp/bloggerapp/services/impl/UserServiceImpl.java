package org.bloggerapp.bloggerapp.services.impl;

import org.bloggerapp.bloggerapp.configs.AppConstant;
import org.bloggerapp.bloggerapp.entities.Role;
import org.bloggerapp.bloggerapp.entities.Users;
import org.bloggerapp.bloggerapp.exceptions.ResourceNotFoundException;
import org.bloggerapp.bloggerapp.payloads.UserDto;
import org.bloggerapp.bloggerapp.repositories.RoleRepo;
import org.bloggerapp.bloggerapp.repositories.UserRepository;
import org.bloggerapp.bloggerapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    private RoleRepo roleRepo;
    private ModelMapper modelMapper;

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        Users users = modelMapper.map(userDto, Users.class);
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Role role = roleRepo.findById(AppConstant.NORMAL_USER).orElseThrow(() -> new ResourceNotFoundException("Role", "Role Id ", AppConstant.NORMAL_USER));
        users.getRoles().add(role);
        Users savedUser = userRepository.save(users);
        return modelMapper.map(savedUser,UserDto.class);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        Users users = userDtoToUser(userDto);
        Users savedUser = userRepository.save(users);
        return userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, int userid) {
        Users users = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userid));
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setAbout(userDto.getAbout());
        Users updatedUser = userRepository.save(users);
        return userToUserDto(updatedUser);
    }

    @Override
    public UserDto getUserById(int userid) {
        Users users = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userid));

        return userToUserDto(users);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<Users> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(users1 -> userToUserDto(users1)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public boolean deleteUser(int userid) {
        Users users = userRepository.findById(userid).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userid));
        userRepository.delete(users);
        return true;
    }

    private Users userDtoToUser(UserDto userDto) {
        Users users = modelMapper.map(userDto, Users.class);
        return users;
       /* Users users = new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        users.setAbout(userDto.getAbout());
        return users;*/
    }

    private UserDto userToUserDto(Users users) {
        return modelMapper.map(users, UserDto.class);
       /* return UserDto.builder().id(users.getId())
                .name(users.getName())
                .email(users.getEmail())
                .password(users.getPassword())
                .about(users.getAbout())
                .build();*/

    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
