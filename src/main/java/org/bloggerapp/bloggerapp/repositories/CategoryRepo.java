package org.bloggerapp.bloggerapp.repositories;

import org.bloggerapp.bloggerapp.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
