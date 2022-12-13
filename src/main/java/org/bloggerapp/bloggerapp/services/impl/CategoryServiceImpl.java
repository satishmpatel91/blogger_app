package org.bloggerapp.bloggerapp.services.impl;

import org.bloggerapp.bloggerapp.entities.Category;
import org.bloggerapp.bloggerapp.exceptions.ResourceNotFoundException;
import org.bloggerapp.bloggerapp.payloads.CategoryDto;
import org.bloggerapp.bloggerapp.repositories.CategoryRepo;
import org.bloggerapp.bloggerapp.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepo categoryRepo;
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepo.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categories", "Category Id : ", categoryId));
        category.setCategoryName(categoryDto.getName());
        category.setCategoryDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepo.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);

    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categories", "Category Id:", categoryId));
        categoryRepo.deleteById(categoryId);
    }

    @Override
    public CategoryDto getCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Categories", "Category Id:", categoryId));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categoryList = categoryRepo.findAll();
        return categoryList.stream().map(category -> modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
    }

    @Autowired
    public void setCategoryRepo(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}
