package org.bloggerapp.bloggerapp;

import lombok.extern.log4j.Log4j2;
import org.bloggerapp.bloggerapp.configs.AppConstant;
import org.bloggerapp.bloggerapp.entities.Role;
import org.bloggerapp.bloggerapp.repositories.RoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Log4j2
public class BloggerAppApplication implements CommandLineRunner {

    private PasswordEncoder passwordEncoder;

    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BloggerAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        log.info(passwordEncoder.encode("ravi123"));
        try {
            Role roleAdmin = new Role();
            roleAdmin.setRoleId(AppConstant.ADMIN_USER);
            roleAdmin.setRoleName("ROLE_ADMIN");

            Role roleNormalUser = new Role();
            roleNormalUser.setRoleId(AppConstant.NORMAL_USER);
            roleNormalUser.setRoleName("ROLE_USER");

            List<Role> roleList = new ArrayList<>();
            roleList.add(roleAdmin);
            roleList.add(roleNormalUser);

            List<Role> result = roleRepo.saveAll(roleList);
            result.forEach(r -> log.info(r.getRoleName()));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Autowired
    public void setRoleRepo(RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
