package org.bloggerapp.bloggerapp.repositories;

import org.bloggerapp.bloggerapp.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {
}
