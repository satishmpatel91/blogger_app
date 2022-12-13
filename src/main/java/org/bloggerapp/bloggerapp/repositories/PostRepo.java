package org.bloggerapp.bloggerapp.repositories;

import org.bloggerapp.bloggerapp.entities.Category;
import org.bloggerapp.bloggerapp.entities.Post;
import org.bloggerapp.bloggerapp.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {

    List<Post> findByUsers(Users users);
    List<Post> findByCategory(Category category);
    @Query("select  p from Post p where p.title like :key")
    List<Post> search(@Param("key") String keyword);
}
