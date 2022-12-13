package org.bloggerapp.bloggerapp.controllers;

import org.bloggerapp.bloggerapp.payloads.CategoryDto;
import org.bloggerapp.bloggerapp.payloads.response.ApiResponse;
import org.bloggerapp.bloggerapp.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> createUpdate(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId) {
        CategoryDto category = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(new ApiResponse(true,String.format("Category with id : %d is deleted",categoryId)),HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer categoryId) {
        CategoryDto category = categoryService.getCategory(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getCategories() {
        List<CategoryDto> categories = categoryService.getCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
